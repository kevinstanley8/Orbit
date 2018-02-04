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
    public static List<MainMenuItem> mainMenuItems = Arrays.asList(
            // Admin gets all menu items
            new MainMenuItem(R.string.menu_add_teacher, R.string.menu_add_teacher, R.drawable.menu_teacher, Constants.ROLE_ADMIN),
            new MainMenuItem(R.string.menu_view_teacher, R.string.menu_view_teacher, R.drawable.menu_choose_student, Constants.ROLE_ADMIN),
            //Teacher Menu Starts
            new MainMenuItem(R.string.menu_add_student,R.string.menu_add_student, R.drawable.menu_student, Constants.ROLE_TEACHER),
            new MainMenuItem(R.string.menu_enroll_student_in_course, R.string.menu_enroll_student_in_course, R.drawable.menu_enroll_student_in_course, Constants.ROLE_TEACHER),
            // Parent Menu Starts
            new MainMenuItem(R.string.menu_choose_student, R.string.menu_choose_student, R.drawable.menu_choose_student, Constants.ROLE_PARENT),
            new MainMenuItem(R.string.menu_link_student,R.string.menu_link_student, R.drawable.menu_link_parent_student, Constants.ROLE_PARENT),

            // Default item for all roles, used for logging off
            new MainMenuItem(R.string.menu_logout, R.string.menu_logout, R.drawable.menu_logout, Constants.DEFAULT));
}
