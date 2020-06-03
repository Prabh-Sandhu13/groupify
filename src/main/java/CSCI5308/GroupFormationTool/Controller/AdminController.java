package CSCI5308.GroupFormationTool.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.Injector;
import CSCI5308.GroupFormationTool.AccessControl.ICourse;
import CSCI5308.GroupFormationTool.AccessControl.ICourseRepository;
import CSCI5308.GroupFormationTool.AccessControl.ICourseService;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserCoursesService;

@Controller
public class AdminController {

	IUserCoursesService userCoursesService;
	ICourseService courseService;

	@GetMapping("/admin/allCourses")
	public String adminCourses(Model model) {
		ICourseRepository courseDB = Injector.instance().getCourseRepository();
		List<ICourse> allCourses = courseDB.getAllCourses();
		model.addAttribute("courses", allCourses);
		return "admin/allCourses";

	}

	@GetMapping("/admin/assignInstructor")
	public String assignInstructor(Model model, @RequestParam(name = "courseId") String courseId) {

		userCoursesService = Injector.instance().getUserCoursesService();
		courseService = Injector.instance().getCourseService();

		ICourse course = courseService.getCourseById(courseId);

		ArrayList<IUser> allUsersCurrentlyNotInstructors = userCoursesService
				.usersCurrentlyNotInstructorsForCourse(courseId);

		model.addAttribute("course", course);
		model.addAttribute("users", allUsersCurrentlyNotInstructors);
		return "admin/assignInstructor";
	}

	@PostMapping("/admin/assignInstructor")
	public String assignInstructorToCourse(@RequestParam(name = "instructor") Long instructor,
			@RequestParam(name = "id") String courseId, Model model) {

		userCoursesService = Injector.instance().getUserCoursesService();

		boolean success = userCoursesService.addInstructorsToCourse(instructor, courseId);

		if (success) {

			return "redirect:/courseList";
		} else {
			model.addAttribute("failure", "Instructor could not be added");
			return "redirect:/admin/assignInstructor?courseId=" + courseId;
		}

	}
}