package com.todo.ToDo.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import com.todo.ToDo.dto.TaskListDto;
import com.todo.ToDo.service.TaskListService;

import java.security.Principal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TaskListController.class})
@ExtendWith(SpringExtension.class)
class TaskListControllerTest {
    @Autowired
    private TaskListController taskListController;

    @MockBean
    private TaskListService taskListService;

    /**
     * Method under test: {@link TaskListController#getTaskLists(Principal)}
     */
    @Test
    void testGetTaskLists() throws Exception {
        when(taskListService.getTaskLists(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/task-list/");
        requestBuilder.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(taskListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TaskListController#deleteTaskList(Principal, Long)}
     */
    @Test
    void testDeleteTaskList() throws Exception {
        doNothing().when(taskListService).deleteTaskList(Mockito.<String>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/task-list/{task_id}", 1L);
        requestBuilder.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(taskListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Deleted task list"));
    }

    /**
     * Method under test: {@link TaskListController#patchTaskList(Principal, Long, TaskListDto)}
     */
    @Test
    void testPatchTaskList() throws Exception {
        doNothing().when(taskListService)
                .patchTaskList(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<TaskListDto>any());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/api/v1/task-list/{task_id}", 1L);
        patchResult.principal(new UserPrincipal("principal"));

        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setId(1L);
        taskListDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(taskListDto);
        MockHttpServletRequestBuilder requestBuilder = patchResult.contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(taskListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Patched success"));
    }

    /**
     * Method under test: {@link TaskListController#postTaskList(Principal, TaskListDto)}
     */
    @Test
    void testPostTaskList() throws Exception {
        when(taskListService.getTaskLists(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/task-list/");
        getResult.principal(new UserPrincipal("principal"));

        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setId(1L);
        taskListDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(taskListDto);
        MockHttpServletRequestBuilder requestBuilder = getResult.contentType(MediaType.APPLICATION_JSON).content(content);
        MockMvcBuilders.standaloneSetup(taskListController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

