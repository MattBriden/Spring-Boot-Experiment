package com.briden.boot.controller;

import com.briden.boot.entity.Entry;
import com.briden.boot.repository.IEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
public class AppControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEntryRepository entryRepository;

    private static final Long COMPANY_ID = 1L;

    @Before
    public void init() {
        when(entryRepository.findByCompanyId(anyLong())).thenReturn(
                Collections.singletonList(new Entry())
        );
    }

    @Test
    public void testFindByCompanyId() throws Exception {
        mockMvc.perform(get("/api/data")
                .param("companyId", COMPANY_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
