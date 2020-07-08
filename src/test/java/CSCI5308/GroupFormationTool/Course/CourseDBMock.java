package CSCI5308.GroupFormationTool.Course;

import CSCI5308.GroupFormationTool.Course.ICourse;
import CSCI5308.GroupFormationTool.Course.ICourseRepository;
import CSCI5308.GroupFormationTool.Course.Course;

import java.util.ArrayList;

public class CourseDBMock implements ICourseRepository {

    private String id;

    private String name;

    private int credits;

    private String description;

    public CourseDBMock() {
        id = "CSCI5308";
        name = "Adv SDC";
        credits = 3;
        description = "sample";
    }

    @Override
    public ArrayList<ICourse> getAllCourses() {
        ArrayList<ICourse> courseList = new ArrayList<>();
        ICourse course = new Course();

        course.setCredits(credits);
        course.setDescription(description);
        course.setName(name);
        course.setId(id);
        courseList.add(course);
        return courseList;
    }

    public ICourse loadCourses(ICourse course) {
        course.setCredits(credits);
        course.setDescription(description);
        course.setId(id);
        course.setName(name);
        return course;
    }

    @Override
    public boolean createCourse(ICourse course) {
        course.setCredits(credits);
        course.setDescription(description);
        course.setName(name);
        course.setId(id);
        return true;
    }

    @Override
    public boolean deleteCourse(String id) {
        if (id.equals("1"))
            return true;
        return false;
    }

    @Override
    public ICourse getCourseById(String courseId) {
        ICourse course = new Course();
        course.setCredits(credits);
        course.setDescription(description);
        course.setId(courseId);
        course.setName(name);
        return course;
    }

}