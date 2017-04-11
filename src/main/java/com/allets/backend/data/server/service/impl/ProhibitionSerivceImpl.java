package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.SlangWords;
import com.allets.backend.data.server.entity.common.SlangWord;
import com.allets.backend.data.server.utils.AwsUploadJob;
import com.allets.backend.data.server.utils.DateUtil;
import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.exception.BadRequestException;
import com.allets.backend.data.server.exception.TextIsTooLongException;
import com.allets.backend.data.server.repository.common.ProhibitionRepository;
import com.allets.backend.data.server.service.ProhibitionSerivce;
import com.allets.backend.data.server.service.UserService;
import com.allets.backend.data.server.utils.AwsUpload;
import com.amazonaws.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jack on 2015/9/1.
 */
@Service
public class ProhibitionSerivceImpl implements ProhibitionSerivce {

    final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);

    @Autowired
    ProhibitionRepository prohibitionRepository;
    @Autowired
    UserService userService;

    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;
    @Resource(name = "redisTemplate")
    private ValueOperations valueOperations;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AwsUpload awsUpload;

    private ExecutorService awsUploads = Executors.newFixedThreadPool(4);

    @Override
    public String selectSlangWordsText() throws Exception {

        String filePath = awsUpload.getFileUploadDir() + File.separator + awsUpload.getFileUploadFileName();
        SlangWords slangWords = new SlangWords();

        String restAppKey = "SLANG_WORD_VERSION";
        valueOperations.increment(restAppKey, 1L);
        slangWords.setVersion(Integer.valueOf(valueOperations.get(restAppKey).toString()));

        slangWords.setGenerationDate(DateUtil.getUTCDate());

        List<SlangWord> resultList = prohibitionRepository.findAllSlangWord(null);
        List<com.allets.backend.data.server.data.result.SlangWord> list = new ArrayList<>();
        if (resultList != null) {
            for (SlangWord s : resultList) {
                com.allets.backend.data.server.data.result.SlangWord slangWord = new com.allets.backend.data.server.data.result.SlangWord();
                slangWord.setSlang(s.getSlang());
                slangWord.setType(s.getType());
                list.add(slangWord);
            }
        }
        slangWords.setSlangWords(list);

        slangWords.setSpecialUsers(userService.seletctSpecialUsers());
        String string = JsonUtil.marshallingJsonWithPretty(slangWords);
        byte[] contentBytes = string.getBytes(StringUtils.UTF8);
        Long contentLength = Long.valueOf(contentBytes.length);
        ByteArrayInputStream bi = new ByteArrayInputStream(contentBytes);
        awsUploads.submit(new AwsUploadJob(awsUpload, awsUpload.getFileUploadFileName(), bi,contentLength));

//        File file = new File(filePath);
//        IOUtil.writeStringToFile(file, JsonUtil.marshallingJsonWithPretty(slangWords));
//        ImmutableList<Pair<String, File>> pairList = ImmutableList.<Pair<String, File>>builder().add(new ImmutablePair<String, File>(awsUpload.getFileUploadFileName(), file)).build();
//        awsUploads.submit(new AwsUploadJob(awsUpload,pairList));

//        List<String> list1 = new ArrayList<String>();
//        list1.add(awsUpload.getFileUploadFileName());
//        awsUpload.delete(list1);

        return awsUpload.getFileUploadFileName();
    }

    @Override
    public List<SlangWord> selectSlangWordList(String field) throws Exception {
        return prohibitionRepository.findAllSlangWord(field);
    }

    @Override
    public SlangWord insertSlangWord(SlangWord slangWord) throws Exception {
        String restAppKey = "SLANG_WORD";
        String hashKey = slangWord.getSlang();
        if (hashKey.length() > 20) {
            throw new TextIsTooLongException();
        }
        String hashValue = slangWord.getType();
        if (hashKey.equals("")) {
            throw new BadRequestException();
        } else if (hashKey.equals("")) {
            throw new BadRequestException();
        }
        if (!hashOperations.hasKey(restAppKey, hashKey)) {
            hashOperations.put(restAppKey, hashKey, hashValue);
        }
        slangWord = prohibitionRepository.save(slangWord);
        selectSlangWordsText();
        return slangWord;
    }

    @Override
    public void updateSlangWord(SlangWord slangWord) throws Exception {
        String restAppKey = "SLANG_WORD";
        String hashKey = slangWord.getSlang();
        if (hashKey.length() > 20) {
            throw new TextIsTooLongException();
        }
        String hashValue = slangWord.getType();
        if (hashKey.equals("")) {
            throw new BadRequestException();
        } else if (hashKey.equals("")) {
            throw new BadRequestException();
        }
        SlangWord slangWordOld = prohibitionRepository.findOne(slangWord.getSlangId());
        hashOperations.delete(restAppKey, slangWordOld.getSlang());
        hashOperations.put(restAppKey, hashKey, hashValue);
        prohibitionRepository.updateSlangWord(slangWord);
        selectSlangWordsText();
    }

    @Override
    public void deleteSlangWord(int slangId) throws Exception {
        SlangWord slangWord = prohibitionRepository.findOne(slangId);
        String restAppKey = "SLANG_WORD";
        String hashKey = slangWord.getSlang();
        String hashValue = slangWord.getType();
        if (hashKey.equals("")) {
            throw new BadRequestException();
        } else if (hashKey.equals("")) {
            throw new BadRequestException();
        } else if (slangWord == null) {
            throw new BadRequestException();
        }
        if (hashOperations.hasKey(restAppKey, hashKey)) {
            hashOperations.delete(restAppKey, hashKey);
        }
        prohibitionRepository.delete(slangId);
        selectSlangWordsText();
    }
}
