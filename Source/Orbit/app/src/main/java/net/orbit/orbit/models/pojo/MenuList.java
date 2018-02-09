package net.orbit.orbit.models.pojo;

/**
 * Created by David on 1/30/2018.
 */

import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.R;

import java.util.Arrays;
import java.util.List;

public class MenuList
{
    public static List<MainMenuItem> adminMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_add_teacher, R.string.menu_add_teacher, R.drawable.menu_teacher),
            new MainMenuItem(R.string.menu_add_student,R.string.menu_add_student, R.drawable.menu_student),
            new MainMenuItem(R.string.menu_enroll_student_in_course, R.string.menu_enroll_student_in_course, R.drawable.menu_enroll_student_in_course),
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student),
            new MainMenuItem(R.string.menu_link_student,R.string.menu_link_student, R.drawable.menu_link_parent_student),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));

    public static List<MainMenuItem> teacherMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_add_student,R.string.menu_add_student, R.drawable.menu_student),
            new MainMenuItem(R.string.menu_enroll_student_in_course, R.string.menu_enroll_student_in_course, R.drawable.menu_enroll_student_in_course),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));

    public static List<MainMenuItem> parentMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student),
            new MainMenuItem(R.string.menu_link_student,R.string.menu_link_student, R.drawable.menu_link_parent_student),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));


    public static List<MainMenuItem> studentMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));


}
