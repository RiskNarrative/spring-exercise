package com.example.SpringExercise.Conroller;

import com.example.SpringExercise.Model.CompanySearchRequest;
import com.example.SpringExercise.Model.CompanySearchResponse;
import com.example.SpringExercise.Service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    public void testSearchCompany() throws Exception {
        // Mock the behavior of CompanyService
        CompanySearchRequest request = new CompanySearchRequest();

        CompanySearchResponse response = new CompanySearchResponse();
        response.setTotalResults(1);

        when(companyService.searchCompany(request, true)).thenReturn(response);

        // Perform the HTTP request against the controller
        mockMvc.perform(MockMvcRequestBuilders.post("/api/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"criteria\": \"example\"}")
                        .param("onlyActive", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
