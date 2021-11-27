package servlet;

import bean.Department;
import bean.Staff;
import dao.DepartmentDao;
import dao.StaffDao;
import utils.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;

@WebServlet(urlPatterns = "/sm")
public class StaffManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 判断是否登录
        HttpSession session = req.getSession();
        if (session.getAttribute("id") != null && (boolean) session.getAttribute("admin")) {
            String doSomething = req.getParameter("do");
            // 删除员工信息
            if ("delete".equals(doSomething)) {
                int id = Integer.parseInt(req.getParameter("id"));
                StaffDao.deleteStaff(id);
            }
            resp.sendRedirect("/admin");
        } else {
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        int age = Integer.parseInt(req.getParameter("age"));
        Date date = Date.valueOf(req.getParameter("date"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        String pos = req.getParameter("pos");
        int dep_id = Integer.parseInt(req.getParameter("dep_id"));

        String str_id = req.getParameter("id");
        // 更新
        if (str_id != null) {
            int id = Integer.parseInt(str_id);
            Staff staff = StaffDao.getStaffById(id);
            staff.setName(name);
            staff.setGender(gender);
            staff.setAge(age);
            staff.setDate(date);
            staff.setSalary(salary);
            staff.setPos(pos);
            staff.setDep_id(dep_id);
            Log.write(staff.toString());
            StaffDao.updateStaff(staff);
        }
        // 添加
        else {
            Staff staff = new Staff();
            staff.setName(name);
            staff.setGender(gender);
            staff.setAge(age);
            staff.setDate(date);
            staff.setSalary(salary);
            staff.setPos(pos);
            staff.setDep_id(dep_id);
            staff.setPwd("123456"); // 默认密码
            StaffDao.insertStaff(staff);
        }
        resp.sendRedirect("/admin");
    }
}
