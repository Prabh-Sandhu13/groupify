package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.Common.Injector;
import CSCI5308.GroupFormationTool.FactoryProducerTest;
import CSCI5308.GroupFormationTool.Password.IPasswordAbstractFactoryTest;
import CSCI5308.GroupFormationTool.Password.PasswordHistoryManager;
import CSCI5308.GroupFormationTool.Password.PolicyRepository;
import CSCI5308.GroupFormationTool.User.IUserAbstractFactoryTest;
import CSCI5308.GroupFormationTool.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerTest {

    private IUserAbstractFactoryTest userAbstractFactoryTest = FactoryProducerTest.getFactory().
            createUserAbstractFactoryTest();

    private IPasswordAbstractFactoryTest passwordAbstractFactoryTest = FactoryProducerTest.getFactory().
            createPasswordAbstractFactoryTest();

    private ISecurityAbstractFactoryTest securityAbstractFactoryTest = FactoryProducerTest.getFactory().
            createSecurityAbstractFactoryTest();

    private PolicyRepository policyRepository;

    private UserRepository userRepository;

    private PasswordHistoryManager passwordHistoryManager;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        policyRepository = passwordAbstractFactoryTest.createPolicyRepositoryMock();
        userRepository = userAbstractFactoryTest.createUserRepositoryMock();
        passwordHistoryManager = passwordAbstractFactoryTest.createPasswordHistoryManagerMock();
        Injector.instance().setPolicyRepository(policyRepository);
        Injector.instance().setUserRepository(userRepository);
        Injector.instance().setPasswordHistoryManager(passwordHistoryManager);
    }

    @Test
    void registerTest() throws Exception {
        when(policyRepository.getPolicies()).thenReturn(passwordAbstractFactoryTest.createPolicyListInstance());
        this.mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/signup"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}