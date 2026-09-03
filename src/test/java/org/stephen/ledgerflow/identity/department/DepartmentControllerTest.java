package org.stephen.ledgerflow.identity.department;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.stephen.ledgerflow.common.exception.DepartmentNotFoundException;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;


@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DepartmentService departmentService;

    @Test
    public void getDepartments_returnsOkAndDepartmentResponses() throws Exception {
        Department engineering = new Department("Engineering","ENG");
        Department finance = new Department("Finance" , "FIN");

        when(departmentService.getDepartments()).thenReturn(List.of(engineering, finance));

        mockMvc.perform(get("/api/v1/departments"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Engineering"))
                .andExpect(jsonPath("$[0].code").value("ENG"))
                .andExpect(jsonPath("$[1].name").value("Finance"))
                .andExpect(jsonPath("$[1].code").value("FIN"));

        verify(departmentService).getDepartments();
    }

    @Test
    public void getDepartment_whenDepartmentExists_returnsOkAndDepartmentResponse() throws Exception {
        Department finance = new Department("Finance" , "FIN");
        UUID id = finance.getId();

        when(departmentService.getDepartment(id)).thenReturn(finance);

        mockMvc.perform(get("/api/v1/departments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Finance"))
                .andExpect(jsonPath("$.code").value("FIN"));

        verify(departmentService).getDepartment(id);
    }

    @Test
    public void getDepartment_whenDepartmentDoesNotExist_returnsNotFoundApiError() throws Exception {
        UUID id = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        when(departmentService.getDepartment(id))
                .thenThrow(new DepartmentNotFoundException(id));

        mockMvc.perform(get("/api/v1/departments/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("DEPARTMENT_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Department '" + id +"' was not found"));

        verify(departmentService).getDepartment(id);
    }

}
