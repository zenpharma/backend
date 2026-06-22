package com.pharma.qc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.qc.model.Inspection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InspectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Inspection buildInspection(String batch, String product, String type, String inspector) {
        Inspection i = new Inspection();
        i.setBatchNumber(batch);
        i.setProductName(product);
        i.setInspectionType(type);
        i.setInspectorName(inspector);
        i.setResult(Inspection.Result.PENDING);
        return i;
    }

    @Test
    void createAndGetInspection() throws Exception {
        Inspection inspection = buildInspection("BATCH-001", "Aspirin", "Visual", "John Doe");

        String body = mockMvc.perform(post("/api/qc/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inspection)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchNumber").value("BATCH-001"))
                .andExpect(jsonPath("$.result").value("PENDING"))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(body).get("id").asLong();

        mockMvc.perform(get("/api/qc/inspections/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Aspirin"));
    }

    @Test
    void updateInspectionResult() throws Exception {
        Inspection inspection = buildInspection("BATCH-002", "Ibuprofen", "Chemical", "Jane Smith");

        String body = mockMvc.perform(post("/api/qc/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inspection)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(body).get("id").asLong();
        inspection.setResult(Inspection.Result.PASS);

        mockMvc.perform(put("/api/qc/inspections/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inspection)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("PASS"));
    }

    @Test
    void deleteInspection() throws Exception {
        Inspection inspection = buildInspection("BATCH-003", "Paracetamol", "Microbiological", "Bob Lee");

        String body = mockMvc.perform(post("/api/qc/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inspection)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(body).get("id").asLong();

        mockMvc.perform(delete("/api/qc/inspections/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Inspection deleted successfully"));
    }

    @Test
    void filterByResult() throws Exception {
        Inspection inspection = buildInspection("BATCH-004", "Amoxicillin", "Visual", "Alice Wong");
        inspection.setResult(Inspection.Result.FAIL);

        mockMvc.perform(post("/api/qc/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inspection)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/qc/inspections?result=FAIL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].result").value("FAIL"));
    }

    @Test
    void createInspectionValidationFails() throws Exception {
        Inspection inspection = new Inspection();

        mockMvc.perform(post("/api/qc/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inspection)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNotFoundReturns404() throws Exception {
        mockMvc.perform(get("/api/qc/inspections/99999"))
                .andExpect(status().isNotFound());
    }
}
