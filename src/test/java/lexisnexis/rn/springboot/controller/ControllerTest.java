package lexisnexis.rn.springboot.controller;

import com.google.gson.GsonBuilder;
import lexisnexis.rn.springboot.service.CompanyService;
import lexisnexis.rn.springboot.utils.CompanySearchRequest;
import lexisnexis.rn.springboot.utils.CompanySearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void findCompanyTest() throws Exception {
        // Mock the behavior of CompanyService
        CompanySearchRequest request = new CompanySearchRequest();

        CompanySearchResponse response = new CompanySearchResponse();
        response.setTotalResults(1);

        when(companyService.findCompany(anyString(), any(), any())).thenReturn(response);

        // Perform the HTTP request against the controller
        mockMvc.perform(MockMvcRequestBuilders.post("/api/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"criteria\": \"example\"}")
                        .param("onlyActive", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void returnHello_success() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/companies/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("print me"));
    }
}
