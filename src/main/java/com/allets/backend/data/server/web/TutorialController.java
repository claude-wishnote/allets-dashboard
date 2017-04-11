package com.allets.backend.data.server.web;

import com.allets.backend.data.server.data.dto.EmpDeptDTO;
import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.facade.EmpFacade;
import com.allets.backend.data.server.utils.MessageUtil;
import com.allets.backend.data.server.entity.common.Emp;
import com.allets.backend.data.server.utils.DownloadObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class TutorialController.
 */
@Controller
@RequestMapping("/tutorial")
public class TutorialController {

	/** The log. */
	final Logger log = LoggerFactory.getLogger(TutorialController.class);
	
	@Autowired
    EmpFacade empFacade;
	
	@Autowired
	private MessageUtil messageUtil;
	
	/**
	 * No layout page. http://localhost:8080/tutorial/emp/noLayout
	 * 
	 * @param model
	 *            the model
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "emp/noLayout", method = RequestMethod.GET)
	public void noLayoutPage(Model model) throws Exception {
	}

	/**
	 * List page. http://localhost:8080/tutorial/emp/list
	 * 
	 * @param model
	 *            the model
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "emp/list", method = RequestMethod.GET)
	public void listPage(Model model, HttpServletRequest request) throws Exception {
		Page<EmpDeptResult> results = empFacade.findEmpDeptAll();
		model.addAttribute("results", results);
	}

	/**
	 * Adds the page.
	 *
	 * http://localhost:8080/tutorial/emp/add
	 * 
	 * @param model
	 *            the model
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "emp/add", method = RequestMethod.GET)
	public void addPage(Model model) throws Exception {
		model.addAttribute("emp", new Emp());
	}

	/**
	 * 
	 * http://localhost:8080/tutorial/emp/add
	 *
	 * @param emp
	 *            the emp
	 * @return the string
	 */
	@RequestMapping(value = "/emp/add", method = RequestMethod.POST)
	public String addingEmp(@ModelAttribute Emp emp) throws Exception {
		
		EmpDeptDTO dto = new EmpDeptDTO();
		dto.setEmp(emp);
		
		empFacade.createEmp(dto);
		return "redirect:/tutorial/emp/list";
	}

	/**
	 * Edits the page.
	 * http://localhost:8080/tutorial/emp/edit/1234
	 * @param model the model
	 * @param id the id
	 */
	@RequestMapping(value = "/emp/edit/{empno}", method = RequestMethod.GET)
	public String editPage(Model model, @PathVariable Integer empno) throws Exception{
		
		Emp emp = empFacade.findEmpByEmpno(empno);
		model.addAttribute("emp", emp);
		
		return "tutorial/emp/edit";
	}

	/**
	 * Editing emp.
	 * http://localhost:8080/tutorial/emp/edit/1234
	 * @param empno the empno
	 * @return the string
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "emp/edit/{empno}", method = RequestMethod.POST)
	public String editingEmp(@ModelAttribute Emp emp, @PathVariable Integer empno) throws Exception {
		
		EmpDeptDTO dto = new EmpDeptDTO();
		dto.setEmp(emp);
		empFacade.modifyEmp(dto);
		
		return "redirect:/tutorial/emp/list";
	}
	
	
	/**
	 * Deleting emp.
	 *
	 * http://localhost:8080/tutorial/emp/delete/12121
	 * 
	 * @param empno
	 *            the empno
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "emp/delete/{empno}", method = RequestMethod.GET)
	public String deletingEmp(@PathVariable Integer empno) throws Exception {
		
		empFacade.removeEmp(empno);
		
		return "redirect:/tutorial/emp/list";
	}

	/**
	 * Json.
	 * 
	 * http://localhost:8080/tutorial/json
	 *
	 * @param model
	 *            the model
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET, produces = { "application/json" })
	public void json(Model model) throws Exception {
		List<Emp> lists = new ArrayList<Emp>();

		// 페이지 번호 설정
		Emp emp1 = new Emp();
		emp1.setEmpno(1);
		emp1.setEname("ncp1");
		emp1.setJob("job1");

		Emp emp2 = new Emp();
		emp2.setEmpno(2);
		emp2.setEname("ncp2");
		emp2.setJob("job2");

		lists.add(emp1);
		lists.add(emp2);

		// 목록 객체 초기화
		model.addAttribute("results", lists);
		model.addAttribute("message", "hello json!!");
	}

	/**
	 * Download. http://localhost:8080/tutorial/download
	 * 
	 * @param model
	 *            the model
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET, produces = { "application/octet-stream" })
	public void download(Model model) throws Exception {

		String downloadPath = "/etc/paths";
		File downloadFile = new File(downloadPath);

		DownloadObject downloadObject = new DownloadObject();
		downloadObject.setDisplayFileName("paths.log");
		downloadObject.setDownloadFile(downloadFile);

		model.addAttribute("file", downloadObject);

	}
	
	/**
	 * Message.http://localhost:8080/tutorial/message
	 *
	 * @param model the model
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "message", method = RequestMethod.GET, produces = { "application/json" })
	public void message(Model model) throws Exception {
		model.addAttribute("message", messageUtil.getMessage("backend.ums.server.hello"));
	}
	
	/**
	 * Json exception.http://localhost:8080/tutorial/jsonException
	 *
	 * @param model the model
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "jsonException", method = RequestMethod.GET, produces = { "application/json" })
	public void jsonException(Model model) throws Exception {
		
		if (true) {
			throw new NotFoundUserException();
		}
		
	}
	
	/**
	 * Jsp exception. http://localhost:8080/tutorial/jspException
	 *
	 * @param model the model
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "jspException", method = RequestMethod.GET)
	public void jspException(Model model) throws Exception {
		
		if (true) {
			throw new NotFoundUserException();
		}
	}
	
}
