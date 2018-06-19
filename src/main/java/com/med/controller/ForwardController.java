import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin("*")
public class ForwardController {

	@RequestMapping(value = {"workplace", "", "/", "/{[path:(?!public).*}/**"})
	public String indexAction() {
		System.out.println("Redirecting");
	    return "/";
	}
	
//	@GetMapping(value = "/**/{[path:[^\\.]*}")
//    public ModelAndView forward() {
//        return new ModelAndView("/index.html");
//    }
    
//    public String redirect() {
//        // Forward to home page so that route is preserved.
//        return "forward:/";
//    }
} 