package com.allets.backend.data.server.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * File & IO 관련 유틸리티 서비스
 * <p/>
 * <p/>
 * .
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 3
 */
public class IOUtil {

	/*
     * ---------------------------------------------------------------------
	 * Instance fields.
	 * ---------------------------------------------------------------------
	 */

    /**
     * UTF-8 인코딩.
     */
    static final String FILE_DEFAULT_ENCODING = "UTF-8";

    /**
     * 한글 인코딩.
     */
    static final String FILE_KO_ENCODING = "MS949";

    /**
     * The constant EXTENSION_SEPARATOR.
     */
    static final char EXTENSION_SEPARATOR = '.';

    /**
     * The constant UNIX_SEPARATOR.
     */
    static final char UNIX_SEPARATOR = '/';

    /**
     * The constant WINDOWS_SEPARATOR.
     */
    static final char WINDOWS_SEPARATOR = '\\';

	/*
	 * ---------------------------------------------------------------------
	 * Constructors.
	 * ---------------------------------------------------------------------
	 */
	/*
	 * ---------------------------------------------------------------------
	 * public & interface method.
	 * ---------------------------------------------------------------------
	 */

    /**
     * 파일 사이즈를 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().getFileLength(new File("c:\a.txt"));
     *
     * @param file 소스 파일
     * @return Int 파일 사이즈
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static long getFileLength(File file) throws Exception {
        FileInputStream input = new FileInputStream(file);
        return getFileLength(input);
    }

    /**
     * 전체 파일 목록을 구한다. 단, 하위 디렉토리 파일 포함
     * <p/>
     * <p/>
     * <p/>
     * <pre>
     * ArrayList&lt;File&gt; listFiles = new ArrayList&lt;File&gt;();
     * FileUtil.getAllFiles(new File(&quot;&quot;), listFiles);
     * </pre>
     *
     * @param file      검색할 디렉토리
     * @param listFiles 전체 파일 리스트
     * @since 2015. 3. 3
     */
    public static void getAllFiles(final File file,
                                   final ArrayList<File> listFiles) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File nextFile : files) {
                getAllFiles(nextFile, listFiles);
            }
        } else {
            listFiles.add(file);
        }
    }

    /**
     * 디렉토리 및 하위 디렉토리의 파일을 전체 삭제한다.
     * <p/>
     * <p/>
     * <p/>
     * <pre>
     * FileUtil.deleteDirectory(new File(&quot;&quot;));
     * </pre>
     *
     * @param file 삭제 디렉토리
     * @since 2015. 3. 3
     */
    public static void deleteAllDirectory(final File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File nextFile : files) {
                deleteAllDirectory(nextFile);
            }
        }

        file.delete();
    }

    /**
     * 파일 확장자를 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().getExtension("foo.txt") --> "txt" <br/>
     * getIoService().getExtension("a/b/c.jpg") --> "jpg"<br/>
     * getIoService().getExtension("a/b.txt/c") --> ""<br/>
     * getIoService().getExtension("a/b/c") --> ""<br/>
     *
     * @param filename 파일명
     * @return String 확장자
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String getExtension(String filename) throws Exception {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * 파일 확장자를 삭제 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().removeExtension("foo.txt") --> "foo"<br/>
     * getIoService().removeExtension("a/b/c.jpg") --> "a/b/c"<br/>
     * getIoService().removeExtension("a/b/c") --> "a/b/c"<br/>
     * getIoService().removeExtension("a.b/c") --> "a.b/c"<br/>
     *
     * @param filename [설명]
     * @return String [설명]
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String removeExtension(String filename) throws Exception {
        return FilenameUtils.removeExtension(filename);
    }

    /**
     * 확장자 유무 체크를 한다.
     * <p/>
     * <p/>
     * getIoService().isExtension("a.txt", "txt") = "true"
     *
     * @param filename  파일 명
     * @param extension 체크 확장자
     * @return true:존재 할 경우, false:존재하지 않을 경우
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static boolean isExtension(String filename, String extension)
            throws Exception {
        return FilenameUtils.isExtension(filename, extension);
    }

    /**
     * 확장자 유무 체크를 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().isExtension("a.txt", {"txt","jpg"}) = "true"
     *
     * @param filename  파일 명
     * @param extension 체크 확장자 배열
     * @return true:존재 할 경우, false:존재하지 않을 경우
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static boolean isExtension(String filename, String[] extension)
            throws Exception {
        return FilenameUtils.isExtension(filename, extension);
    }

    /**
     * 확장자를 포함한 파일명을 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().getName("a/b/c.txt") = "c.txt"
     * getIoService().getName("a/b/c") = "c" getIoService().getName("a/b/c/") =
     * ""
     *
     * @param fileName 전체 full 경로 파일 명
     * @return String 파일 명
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String getName(String fileName) throws Exception {
        return FilenameUtils.getName(fileName);
    }

    /**
     * 확장자를 제외한 파일명을 린턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().getName("a/b/c.txt") = "c" getIoService().getName("a.txt")
     * = "a" getIoService().getName("a/b/c/") = ""
     *
     * @param fileName 전체 full 경로 파일 명
     * @return String 파일 명
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String getBaseName(String fileName) throws Exception {
        return FilenameUtils.getBaseName(fileName);
    }

    /**
     * 파일 내용을 UTF-8 포맷으로 인코딩 후 스트링으로 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().readFileToStringWithUTF8(new File("c:/ESRep.log"))
     *
     * @param file 소스 파일
     * @return String utf-8로 인코딩된 스트링
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String readFileToStringWithUTF8(File file) throws Exception {
        return FileUtils.readFileToString(file, FILE_DEFAULT_ENCODING);
    }

    /**
     * 파일 내용을 한글(MS949) 포맷으로 인코딩 후 스트링으로 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().readFileToStringWithKor(new File("c:/ESRep.log"))
     *
     * @param file 소스 파일
     * @return String MS949
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String readFileToStringWithKor(File file) throws Exception {
        return FileUtils.readFileToString(file, FILE_KO_ENCODING);
    }

    /**
     * 파일 내용을 UTF-8 포맷으로 인코딩 후 라인 단위로 List<String>을 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().readLineWithUTF8(new File("c:/ESRep.log"))
     *
     * @param file 소스 파일
     * @return List utf-8로 인코딩된 스트링
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static List<String> readLineWithUTF8(File file) throws Exception {
        return FileUtils.readLines(file, FILE_DEFAULT_ENCODING);
    }

    /**
     * 파일 내용을 한글(MS949) 포맷으로 인코딩 후 라인 단위로 List<String>을 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().readLineWithKor(new File("c:/ESRep.log"))
     * <p/>
     * <p/>
     * <p/>
     * [사용 방법 설명].
     *
     * @param file 소스 파일
     * @return List utf-8로 인코딩된 스트링
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static List<String> readLineWithKor(File file) throws Exception {
        return FileUtils.readLines(file, FILE_KO_ENCODING);
    }

    /**
     * 스트링 데이터를 파일에 utf-8 인코딩으로 write 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().writeStringToFile(new File("c:/ESRep.log"),"hello")
     *
     * @param file 대상 파일
     * @param data 저장할 스트링 데이터
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static void writeStringToFile(File file, String data)
            throws Exception {
        FileUtils.writeStringToFile(file, data, FILE_DEFAULT_ENCODING);
    }

    /**
     * 스트링 데이터를 파일에 설정한 인코딩으로 write 한다.
     * <p/>
     * <p/>
     * 한글로 인코딩 할경우는 MS949 인코딩 한다. <br/>
     * getIoService().writeStringToFile(new File("c:/ESRep.log"),"hello",
     * "MS949")
     *
     * @param file   대상 파일
     * @param data   저장할 스트링 데이터
     * @param encode 인코딩 타입
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static void writeStringToFile(File file, String data, String encode)
            throws Exception {
        FileUtils.writeStringToFile(file, data, encode);
    }

    /**
     * 스트링 리스트 오브젝트를 데이터를 파일에 utf-8 인코딩으로 write 한다.
     * <p/>
     * <p/>
     * List list = new ArrayList();<br/>
     * list.add("a");<br/>
     * getIoService().appendStringToFile(new File("c:/ESRep.log"),list, true)
     *
     * @param file 대상 파일
     * @param data 저장할 스트링 데이터 리스트
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static void appendStringToFile(File file, List<String> data)
            throws Exception {
        FileUtils.writeLines(file, data, FILE_DEFAULT_ENCODING, true);
    }

    /**
     * <p/>
     * List list = new ArrayList();<br/>
     * list.add("a");<br/>
     * getIoService().appendStringToFile(new File("c:/ESRep.log"),list, "MS949")
     *
     * @param file     대상 파일
     * @param data     저장할 스트링 데이터 리스트
     * @param encoding 인코딩 타입
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static void appendStringToFile(File file, List<String> data,
                                          String encoding) throws Exception {
        FileUtils.writeLines(file, data, encoding, true);
    }

    /**
     * 파일을 지정된 디렉토리에 옮긴다. 만약 디렉토리가 존재 하지 않을 경우 디렉토리를 생성 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().moveFile(new File("c:/ESRep3.log"), new File("c:/temp2"));
     *
     * @param srcFile  소스 파일
     * @param destFile 복사할 디렉토리
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static void moveFile(File srcFile, File destFile) throws Exception {
        FileUtils.moveFileToDirectory(srcFile, destFile, true);
    }

    public static void moveFileToFile(File srcFile, File destFile) throws Exception {
        FileUtils.moveFile(srcFile, destFile);
    }

    /**
     * 파일에서 FileInputStream 스트림을 생성 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().openInputStream(new File("c:/ESRep3.log"));
     *
     * @param file 소스 파일
     * @return File input stream 스트림
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static FileInputStream openInputStream(File file) throws Exception {
        return FileUtils.openInputStream(file);
    }

    /**
     * 파일에서 OpenOutputStream 스트림을 생성 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().openOutputStream(new File("c:/ESRep3.log"));
     *
     * @param file 소스 파일
     * @return File output stream 스트림
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static FileOutputStream openOutputStream(File file) throws Exception {
        return FileUtils.openOutputStream(file);
    }

    /**
     * 파일을 복사 한다.
     * <p/>
     * <p/>
     * <p/>
     * getIoService().copyFile(new File("c:/ESRep3.log"), new
     * File("c:/ESRep4.log"));
     *
     * @param source 소스 파일
     * @param dest   복사될 파일
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static void copyFile(File source, File dest) throws Exception {
        FileUtils.copyFile(source, dest);
    }

    /**
     * InputStream을 설정된 인코딩 후 스트링으로 리턴 한다.
     * <p/>
     * <p/>
     *
     * @param input    스트림
     * @param encoding 인코딩 타입
     * @return String 리턴 스트링
     * @throws Exception the exception
     * @since 2015. 3. 3
     */
    public static String toString(InputStream input, String encoding)
            throws Exception {
        return IOUtils.toString(input, encoding);
    }

    /**
     * 업로드 파일을 지정된 위치에 저장 한다.
     * <p/>
     * <p/>
     *
     * @param multipartFile [설명]
     * @param path          [설명]
     * @param fileName      [설명]
     * @throws Exception the exception
     * @since 2015. 3. 24
     */
    public static String saveUploadFile(MultipartFile multipartFile, String path,
                                        String fileName) throws Exception {

        File pFile = new File(path);
        boolean available = true;

        // 디렉토리가 존재 하지 않을 경우 생성
        if (!pFile.exists()) {
            pFile.mkdirs();
        }

        String uploadFile = trimToPath(path) + fileName;
        multipartFile.transferTo(new File(uploadFile));

        return uploadFile;
    }

    /**
     * 디렉토리 마지막에 구분자가 없을 경우 구분자를 추가해 준다..
     * <p/>
     * c:/aaa/bbb => c:/aaa/bbb/ c:/aaa/bbb/ => c:/aaa/bbb/
     * <p/>
     *
     * @param path [설명]
     * @return String [설명]
     * @throws Exception the exception
     * @since 2015. 3. 24
     */
    public static String trimToPath(String path) throws Exception {

        String convertPath = path;

        // 맨 위체 구분자가 없을 경우 추가 한다.
        if (!(StringUtils.endsWith(path, "" + UNIX_SEPARATOR) || StringUtils
                .endsWith(path, "" + WINDOWS_SEPARATOR))) {
            convertPath += "" + UNIX_SEPARATOR;
        }


        return convertPath;
    }


   
    /* 현재 request 정보를 가져 온다. */
    public static HttpServletRequest getCurrentRequest() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        return request;
    }

    
    /**
	 * 파일 삭제
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 * @author ys.tam@partner.samsung.com
	 * @since 2015. 4. 29
	 */
	public static void deleteFile(String filePath, String fileName) throws Exception {
		
		File file = new File( filePath , fileName );
		
		if( !file.isDirectory() ){
			FileUtils.deleteQuietly(file);
		}
	}
	
	/*
	 * ---------------------------------------------------------------------
	 * private method.
	 * ---------------------------------------------------------------------
	 */

    private static Long getFileLength(FileInputStream input) throws Exception {
        FileChannel in = input.getChannel();
        return new Long(in.size());
    }

    /**
     * 파입 업로드를 위한 파일 이름 생성
     *
     * @return
     */
    private static String getUniqueFileName() {
        return String.valueOf(System.nanoTime());
    }

    /**
     * 레파지토리 경로
     *
     * @param type
     * @return
     */
    private static String getFilePath(String type) {
		/*
		 * 추후 type별 레파지토리 경로를 설정함.
		 */
        return "c:/upload/";
    }

    /**
     * 파일명를 추출한다. ( ex) kkk.pdf   return kkk )
     *
     * @param fileName
     * @return
     */
    private static String extractFileName(String fileName) {

        //나중에 lastIndex사용

        String temp[] = fileName.split("[.]");
        if (temp.length != 0) return temp[0];
        return "";
    }
}
