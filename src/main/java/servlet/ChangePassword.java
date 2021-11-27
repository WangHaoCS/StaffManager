package servlet;

import bean.Absence;
import bean.Administrator;
import bean.Staff;
import dao.AbsenceDao;
import dao.AdministratorDao;
import dao.StaffDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/password")
public class ChangePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String oldPassword = req.getParameter("old");
        String newPassword = req.getParameter("new");
        HttpSession session = req.getSession();
        if (session.getAttribute("id") != null && !(boolean) session.getAttribute("admin")) {
            int id = (int) session.getAttribute("id");
            Staff staff = StaffDao.getStaffById(id);
            if (staff.getPwd().equals(oldPassword)) {
                staff.setPwd(newPassword);
                StaffDao.updateStaff(staff);
                session.invalidate();
                resp.sendRedirect("/");
            } else {
                resp.sendRedirect("/staff");
            }
        }
        else if (session.getAttribute("id") != null && (boolean) session.getAttribute("admin")) {
            int id = (int) session.getAttribute("id");
            Administrator admin = AdministratorDao.getAdministartorById(id);
            if (admin.getPwd().equals(oldPassword)) {
                admin.setPwd(newPassword);
                AdministratorDao.updateAdministrator(admin);
                session.invalidate();
                resp.sendRedirect("/");
            } else {
                resp.sendRedirect("/admin");
            }
        }
        else {
            resp.sendRedirect("/");
        }
    }
}
