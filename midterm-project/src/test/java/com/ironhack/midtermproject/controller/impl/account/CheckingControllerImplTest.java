package com.ironhack.midtermproject.controller.impl.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermproject.controller.dto.account.CheckingDTO;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.repository.account.CheckingRepository;
import com.ironhack.midtermproject.repository.user.AccountHolderRepository;
import com.ironhack.midtermproject.repository.user.UserRepository;
import com.ironhack.midtermproject.utils.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CheckingControllerImplTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        AccountHolder primaryOwner = new AccountHolder();
        primaryOwner.setName("Sarah Fossi");
        accountHolderRepository.save(primaryOwner);

        userRepository.save(primaryOwner);
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        checkingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createNewAccount() throws Exception {
        CheckingDTO checkingDTO = new CheckingDTO(accountHolderRepository.findByName("Sarah Fossi").get().getId()
                , null, new Money(new BigDecimal(80000)), "cbwuinquinw&2@xisni");
        String body = objectMapper.writeValueAsString(checkingDTO);

        MvcResult mvcResult = mockMvc.perform(post("/accounts/")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("80000"));
    }

    @Test
    void findById() throws Exception {
    }
}