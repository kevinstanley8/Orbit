package net.orbit.orbit.models;

/**
 * Created by David on 1/30/2018.
 */

import net.orbit.orbit.R;

import java.util.Arrays;
import java.util.List;

public class MenuList
{
    public static List<MainMenuItem> mainMenuItems = Arrays.asList(
            // Admin gets all menu items
            new MainMenuItem(R.string.menu_add_teacher, R.string.menu_add_teacher, R.drawable.menu_teacher),
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student),
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student),
            //Teacher Menu Starts
            new MainMenuItem(R.string.menu_add_student,R.string.menu_add_student, R.drawable.menu_student),
            new MainMenuItem(R.string.menu_link_student,R.string.menu_link_student, R.drawable.menu_link_parent_student),
            new MainMenuItem(R.string.menu_enroll_student_in_course, R.string.menu_enroll_student_in_course, R.drawable.menu_enroll_student_in_course),
            // Default item for all roles, used for logging off
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout));
}
