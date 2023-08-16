package com.todo.ToDo.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import com.todo.ToDo.dto.TaskDto;
import com.todo.ToDo.dto.TaskListDto;
import com.todo.ToDo.dto.TaskResponse;
import com.todo.ToDo.model.TaskStatus;
import com.todo.ToDo.service.TaskService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

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

@ContextConfiguration(classes = {TaskController.class})
@ExtendWith(SpringExtension.class)
class TaskControllerTest {
    @Autowired
    private TaskController taskController;

    @MockBean
    private TaskService taskService;

    @Test
    void testGetTasks() throws Exception {
        TaskResponse.TaskResponseBuilder builderResult = TaskResponse.builder();
        TaskListDto taskList = TaskListDto.builder().id(1L).name("Name").build();
        TaskResponse.TaskResponseBuilder taskListResult = builderResult.taskList(taskList);
        TaskResponse.TaskResponseBuilder tasksResult = taskListResult.tasks(new ArrayList<>());
        TaskResponse buildResult = tasksResult.timestamp(LocalDate.of(1970, 1, 1)).build();
        when(taskService.getTasks(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/task-list/{task_list_id}", 1L);
        requestBuilder.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"taskList\":{\"id\":1,\"name\":\"Name\"},\"tasks\":[],\"timestamp\":[1970,1,1]}"));
    }

    @Test
    void testPostTask2() throws Exception {
        doNothing().when(taskService).postTask(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<TaskDto>any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/task-list/{task_list_id}", 1L);
        postResult.principal(new UserPrincipal("principal"));

        TaskDto taskDto = new TaskDto();
        taskDto.setDeadline(null);
        taskDto.setDescription("The characteristics of someone or something");
        taskDto.setId(1L);
        taskDto.setStatus(TaskStatus.TODO);
        taskDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(taskDto);
        MockHttpServletRequestBuilder requestBuilder = postResult.contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Created new task"));
    }

    @Test
    void testGetTask() throws Exception {
        TaskResponse.TaskResponseBuilder builderResult = TaskResponse.builder();
        TaskListDto taskList = TaskListDto.builder().id(1L).name("Name").build();
        TaskResponse.TaskResponseBuilder taskListResult = builderResult.taskList(taskList);
        TaskResponse.TaskResponseBuilder tasksResult = taskListResult.tasks(new ArrayList<>());
        TaskResponse buildResult = tasksResult.timestamp(LocalDate.of(1970, 1, 1)).build();
        when(taskService.getTask(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/task-list/{task_list_id}/task/{task_id}", 1L, 1L)
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"taskList\":{\"id\":1,\"name\":\"Name\"},\"tasks\":[],\"timestamp\":[1970,1,1]}"));
    }

    @Test
    void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/task-list/{task_list_id}/task/{task_id}", 1L, 1L);
        requestBuilder.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete task success"));
    }

    @Test
    void testPatchTask2() throws Exception {
        doNothing().when(taskService)
                .patchTask(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<TaskDto>any());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders
                .patch("/api/v1/task-list/{task_list_id}/task/{task_id}", 1L, 1L);
        patchResult.principal(new UserPrincipal("principal"));

        TaskDto taskDto = new TaskDto();
        taskDto.setDeadline(null);
        taskDto.setDescription("The characteristics of someone or something");
        taskDto.setId(1L);
        taskDto.setStatus(TaskStatus.TODO);
        taskDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(taskDto);
        MockHttpServletRequestBuilder requestBuilder = patchResult.contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Patched success"));
    }

    /**
     * Method under test: {@link TaskController#patchTask(Principal, Long, Long, TaskDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPatchTask() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.todo.ToDo.dto.TaskDto["deadline"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1300)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:728)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4568)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3821)
        //   See https://diff.blue/R013 to resolve this issue.

        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .patch("/api/v1/task-list/{task_list_id}/task/{task_id}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON);
        LocalDate deadline = LocalDate.of(1970, 1, 1);

        TaskDto taskDto = new TaskDto();
        taskDto.setDeadline(deadline);
        taskDto.setDescription("The characteristics of someone or something");
        taskDto.setId(1L);
        taskDto.setStatus(TaskStatus.TODO);
        taskDto.setTitle("Dr");
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(taskDto));
        MockMvcBuilders.standaloneSetup(taskController).build().perform(requestBuilder);
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testPostTask() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.todo.ToDo.dto.TaskDto["deadline"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1300)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:728)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4568)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3821)
        //   See https://diff.blue/R013 to resolve this issue.

        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/api/v1/task-list/{task_list_id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);
        LocalDate deadline = LocalDate.of(1970, 1, 1);

        TaskDto taskDto = new TaskDto();
        taskDto.setDeadline(deadline);
        taskDto.setDescription("The characteristics of someone or something");
        taskDto.setId(1L);
        taskDto.setStatus(TaskStatus.TODO);
        taskDto.setTitle("Dr");
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(taskDto));
        MockMvcBuilders.standaloneSetup(taskController).build().perform(requestBuilder);
    }
}

