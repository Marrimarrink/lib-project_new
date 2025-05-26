package ru.itgirl.libproject_new.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libproject_new.dto.book.response.BookDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBookByNameV1() throws Exception {
        String bookName = "Студент";
        BookDto bookDto = new BookDto();
        bookDto.setId(9L);
        bookDto.setName(bookName);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v1")
                .param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()));
    }

    @Test
    public void testGetBookByNameV2() throws Exception {
        String bookName = "Студент";
        BookDto bookDto = new BookDto();
        bookDto.setId(9L);
        bookDto.setName(bookName);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v2")
                        .param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()));
    }
    @Test
    public void testGetBookByNameV3() throws Exception {
        String bookName = "Студент";
        BookDto bookDto = new BookDto();
        bookDto.setId(9L);
        bookDto.setName(bookName);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v1")
                        .param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()));
    }
}
