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
    public static final List<MainMenuItem> adminMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_add_teacher, R.string.menu_add_teacher, R.drawable.menu_add_teacher),
            new MainMenuItem(R.string.menu_add_student,R.string.menu_add_student, R.drawable.menu_add_student),
            new MainMenuItem(R.string.menu_enroll_student_in_course, R.string.menu_enroll_student_in_course, R.drawable.menu_enroll_student_in_course),
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student),
            new MainMenuItem(R.string.menu_link_student,R.string.menu_link_student, R.drawable.menu_link_parent_student),
            new MainMenuItem(R.string.report_a_bug, R.string.report_a_bug, R.drawable.ic_menu_report_bug),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));

    public static final List<MainMenuItem> teacherMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_add_student,R.string.menu_add_student, R.drawable.menu_add_student),
            new MainMenuItem(R.string.menu_enroll_student_in_course, R.string.menu_enroll_student_in_course, R.drawable.ic_menu_enroll_student_in_course),
            new MainMenuItem(R.string.choose_course,R.string.choose_course, R.drawable.menu_choose_course),
            new MainMenuItem(R.string.view_course,R.string.view_course, R.drawable.menu_view_courses),
            new MainMenuItem(R.string.view_assignments,R.string.view_assignments, R.drawable.menu_view_assignment),
            new MainMenuItem(R.string.create_assignment,R.string.create_assignment, R.drawable.ic_menu_create_assignment),
            new MainMenuItem(R.string.report_a_bug, R.string.report_a_bug, R.drawable.ic_menu_report_bug),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));

    public static final List<MainMenuItem> parentMenuList = Arrays.asList(
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student),
            new MainMenuItem(R.string.menu_link_student,R.string.menu_link_student, R.drawable.menu_link_parent_student),
            new MainMenuItem(R.string.report_a_bug, R.string.report_a_bug, R.drawable.ic_menu_report_bug),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));


    public static final List<MainMenuItem> studentMenuList = Arrays.asList(
            new MainMenuItem(R.string.report_a_bug, R.string.report_a_bug, R.drawable.ic_menu_report_bug),
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));


}
