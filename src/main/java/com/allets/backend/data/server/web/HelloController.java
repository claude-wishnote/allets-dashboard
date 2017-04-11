package com.allets.backend.data.server.web;

import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.dto.MonitorDTO;
import com.allets.backend.data.server.facade.SecurityFacade;
import com.allets.backend.data.server.consts.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by claude on 2015/8/18.
 */
@Controller
@RequestMapping()
public class HelloController {

    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    SecurityFacade securityFacade;

    /*
    set welcome html file
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView indexPage(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("/ums/login");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("/ums/login");
        return mav;
    }

    @RequestMapping(value = "/accountInfo", method = RequestMethod.GET)
    public ModelAndView accountInfo(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/account");
        return mav;
    }

    @RequestMapping(value = "/accountInfoCS", method = RequestMethod.GET)
    public ModelAndView accountInfoCS(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        } else if (monitorDTO.getLevel().equals(Level.ADMIN)) {
            return new ModelAndView("/ums/account3");
        } else {
            return new ModelAndView("/ums/account");
        }
    }

    @RequestMapping(value = "/monitors", method = RequestMethod.GET)
    public ModelAndView monitors(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        if (monitorDTO.getLevel().equals(Level.NORMAL)) {
            return new ModelAndView("/ums/authority2");
        }
        ModelAndView mav = new ModelAndView("/ums/authority");
        return mav;
    }

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ModelAndView monitor(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/authority2");
        return mav;
    }

    @RequestMapping(value = "/blacklist", method = RequestMethod.GET)
    public ModelAndView blacklist(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/blacklist");
        return mav;
    }

    @RequestMapping(value = "/handledAccounts", method = RequestMethod.GET)
    public ModelAndView handledAccount(HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/disposal-account");
        return mav;
    }

    @RequestMapping(value = "/handledComments", method = RequestMethod.GET)
    public ModelAndView handledComment(HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/disposal-reply");
        return mav;
    }

    @RequestMapping(value = "/allComments", method = RequestMethod.GET)
    public ModelAndView allComments(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/reply-watch");
        return mav;
    }

    @RequestMapping(value = "/reportedAccounts", method = RequestMethod.GET)
    public ModelAndView reportedAccounts(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/report-account");
        return mav;
    }

    @RequestMapping(value = "/reportedComments", method = RequestMethod.GET)
    public ModelAndView reportedComment(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/report-reply");
        return mav;
    }

    @RequestMapping(value = "/handleRules", method = RequestMethod.GET)
    public ModelAndView handleRules(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/standard2");
        }
        ModelAndView mav = new ModelAndView("/ums/standard");
        return mav;
    }

    @RequestMapping(value = "/slangWords", method = RequestMethod.GET)
    public ModelAndView slangWords(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/prohibition");
        return mav;
    }

    @RequestMapping(value = "/monitorStatistics", method = RequestMethod.GET)
    public ModelAndView monitorStatistics(HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        MonitorDTO monitorDTO = securityFacade.getAuthenticatedMonitor();
        if (monitorDTO == null) {
            return new ModelAndView("/ums/login");
        }
        if (monitorDTO.getLevel().equals(Level.CS)) {
            return new ModelAndView("/ums/account2");
        }
        ModelAndView mav = new ModelAndView("/ums/monitor-statistics");
        return mav;
    }

    @RequestMapping(value = "/const", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void hello(Model model) throws Exception {
        //for users
        List<String> actionsList = new ArrayList<String>();
        actionsList.add(Const.Action.BlACKLIST);
        actionsList.add(Const.Action.OUT);
        actionsList.add(Const.Action.DEL);
        actionsList.add(Const.Action.HIDD);
        actionsList.add(Const.Action.PASS);

        actionsList.add(Const.Action.BlACKLIST);

        actionsList.add(Const.Action.REPORT);
        actionsList.add(Const.Action.REPORTED);

        actionsList.add(Const.Action.HANDLE);
        actionsList.add(Const.Action.HANDLED);

        actionsList.add(Const.Action.UNDEL);
        actionsList.add(Const.Action.UNBlACKLIST);
        actionsList.add(Const.Action.UNOUT);
        actionsList.add(Const.Action.MODIFYPROFILES);
        actionsList.add(Const.Action.ALL);
        actionsList.add(Const.Action.ALLSIMPLE);
        actionsList.add(Const.Action.REPORTANDHANDLE);
        actionsList.add(Const.Action.MODIFYPASSWORD);
        model.addAttribute("actions", actionsList);

        List<String> statusList = new ArrayList<String>();
        statusList.add(Const.Status.PASS);
        statusList.add(Const.Status.DEL);
        statusList.add(Const.Status.OUT);
        statusList.add(Const.Status.HIDD);
        statusList.add(Const.Status.INVD);
        model.addAttribute("status", statusList);
    }

}
