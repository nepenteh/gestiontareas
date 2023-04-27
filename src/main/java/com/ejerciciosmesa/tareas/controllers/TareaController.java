package com.ejerciciosmesa.tareas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.ejerciciosmesa.tareas.util.paginator.PageRender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;



import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import com.ejerciciosmesa.tareas.models.services.UploadService;





import com.ejerciciosmesa.tareas.appdata.AppData;
import com.ejerciciosmesa.tareas.models.entities.Tarea;
import com.ejerciciosmesa.tareas.models.services.TareaService;




@Controller
@SessionAttributes("tarea")
@RequestMapping("/tarea")
public class TareaController {

	private final AppData appData;
	private final TareaService tareaService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "TAREA"; 
	
	public TareaController(UploadService uploadService,
										 
										 
									     TareaService tareaService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.tareaService = tareaService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Tarea> pageTarea = tareaService.findAll(pageRequest); 
		PageRender<Tarea> paginator = new PageRender<>("/tarea/list",pageTarea,5);
		

		model.addAttribute("numtarea", tareaService.count());
		model.addAttribute("listtarea", pageTarea);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "tarea/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Tarea tarea = new Tarea();		
		model.addAttribute("tarea",tarea);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "tarea/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Tarea tarea = tareaService.findOne(id);
		if(tarea==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/tarea/list/" + page;
		}
		
		model.addAttribute("tarea", tarea);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "tarea/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Tarea tarea,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile foto1_formname,
@RequestParam("foto1ImageText") String foto1ImageText,
@RequestParam("foto1ImageTextOld") String foto1ImageTextOld,
@RequestAttribute("file") MultipartFile foto2_formname,
@RequestParam("foto2ImageText") String foto2ImageText,
@RequestParam("foto2ImageTextOld") String foto2ImageTextOld,

					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(tarea.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (tarea.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "tarea/form";
		}
		
		if(!foto1_formname.isEmpty())
	AddUpdateImageFoto1(foto1_formname,tarea);
else {
	if(foto1ImageText.isEmpty() && !(foto1ImageTextOld.isEmpty())) {
		uploadService.delete(foto1ImageTextOld);
		tarea.setFoto1(null);
	}
}


if(!foto2_formname.isEmpty())
	AddUpdateImageFoto2(foto2_formname,tarea);
else {
	if(foto2ImageText.isEmpty() && !(foto2ImageTextOld.isEmpty())) {
		uploadService.delete(foto2ImageTextOld);
		tarea.setFoto2(null);
	}
}



		
		
		
		tareaService.save(tarea);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/tarea/list/" + page;
	}
	
	
	private void AddUpdateImageFoto1(MultipartFile image, Tarea tarea) {
					
			if(tarea.getId()!=null &&
			   tarea.getId()>0 && 
			   tarea.getFoto1()!=null &&
			   tarea.getFoto1().length() > 0) {
			
				uploadService.delete(tarea.getFoto1());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			tarea.setFoto1(uniqueName);
		
	}
private void AddUpdateImageFoto2(MultipartFile image, Tarea tarea) {
					
			if(tarea.getId()!=null &&
			   tarea.getId()>0 && 
			   tarea.getFoto2()!=null &&
			   tarea.getFoto2().length() > 0) {
			
				uploadService.delete(tarea.getFoto2());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			tarea.setFoto2(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Tarea tarea = tareaService.findOne(id);
			
			if(tarea != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				tareaService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/tarea/list/" + page;
			}
			
			if(tarea.getFoto1()!=null)
	uploadService.delete(tarea.getFoto1());
if(tarea.getFoto2()!=null)
	uploadService.delete(tarea.getFoto2());

						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/tarea/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Tarea tarea = tareaService.findOne(id);

			if (tarea == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/tarea/list/" + page;
			}

			model.addAttribute("tarea", tarea);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "tarea/view";
			
		}

		return "redirect:/tarea/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Tarea tarea = tareaService.findOne(id);

			if (tarea == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/tarea/list";
			}

			model.addAttribute("tarea", tarea);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "tarea/viewimg";
			
		}

		return "redirect:/tarea/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = tareaService.count();
		int nPag = (int) (nReg / 10);
		if (nReg % 10 == 0)
			nPag--;
		return nPag;
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

