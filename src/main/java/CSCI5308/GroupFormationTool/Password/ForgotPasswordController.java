package CSCI5308.GroupFormationTool.Password;

import CSCI5308.GroupFormationTool.ErrorHandling.PasswordException;
import CSCI5308.GroupFormationTool.ErrorHandling.PasswordHistoryException;
import CSCI5308.GroupFormationTool.ErrorHandling.TokenExpiredException;
import CSCI5308.GroupFormationTool.ErrorHandling.UserAlreadyExistsException;
import CSCI5308.GroupFormationTool.Common.DomainConstants;
import CSCI5308.GroupFormationTool.Common.Injector;
import CSCI5308.GroupFormationTool.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ForgotPasswordController {

    private IForgotPasswordManager forgotPasswordManager;
    private String receivedToken;
    private IPolicy policyInstance;

    @GetMapping("/forgotPassword")
    public String register(User user) {
        return "password/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public ModelAndView sendMail(User user) {
        ModelAndView modelAndView = null;

        try {
            forgotPasswordManager = Injector.instance().getForgotPasswordManager();
            forgotPasswordManager.notifyUser(user);
            modelAndView = new ModelAndView("password/MailSentSuccess");
            modelAndView.addObject("Success", DomainConstants.mailSentSuccess);

        } catch (UserAlreadyExistsException uaex) {
            modelAndView = new ModelAndView("password/forgotPassword");
            modelAndView.addObject("userAlreadyExists", DomainConstants.userDoesNotExists
            		.replace("[[emailId]]", user.getEmailId()));
        } catch (Exception e) {
        }
        return modelAndView;
    }

    @GetMapping("/resetPassword")
    public String reset(User user, @RequestParam(name = "token", required = false) String token, Model model) {
        receivedToken = token;
        policyInstance = Injector.instance().getPolicy();
        ArrayList<IPolicy> policies = policyInstance.getPolicies();
        model.addAttribute("policies", policies);
        return "password/resetPassword";
    }

    @PostMapping("/resetPassword")
    public ModelAndView updatePassword(User user) {
        ModelAndView modelAndView = null;
        try {
            forgotPasswordManager = Injector.instance().getForgotPasswordManager();
            forgotPasswordManager.updatePassword(user, receivedToken);
            modelAndView = new ModelAndView("password/passwordResetSuccess");
            modelAndView.addObject("Success", DomainConstants.passwordResetMessage);
        } catch (TokenExpiredException tee) {
            modelAndView = new ModelAndView("password/resetPassword");
            modelAndView.addObject("Error", DomainConstants.tokenExpiredMessage);
        } catch (PasswordException pex) {
            modelAndView = new ModelAndView("redirect:/resetPassword");
            modelAndView.addObject("token", receivedToken);
            modelAndView.addObject("passwordError", pex.getMessage());
        } catch (PasswordHistoryException phe) {
            modelAndView = new ModelAndView("redirect:/resetPassword");
            modelAndView.addObject("token", receivedToken);
            modelAndView.addObject("passwordError", phe.getMessage());
        } catch (Exception e) {
            modelAndView = new ModelAndView("password/resetPassword");
            modelAndView.addObject("Error", DomainConstants.error);
        }
        return modelAndView;
    }
}