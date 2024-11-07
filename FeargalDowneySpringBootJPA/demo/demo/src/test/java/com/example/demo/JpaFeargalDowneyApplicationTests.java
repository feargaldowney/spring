package com.example.demo;

import com.example.demo.controllers.DTOS.TenantMoveDTO;
import com.example.demo.entities.Property;
import com.example.demo.entities.Tenant;
import com.example.demo.repositories.PropertyRepository;
import com.example.demo.repositories.TenantRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JpaFeargalDowneyApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PropertyRepository propertyRepository;
	@MockBean
	TenantRepository tenantRepository;

	@Test
	void findByIdifExists() throws Exception {
		Property disneyWorld = new Property("DisneyWorld", 20, 4000);
		given(propertyRepository.findById(1)).willReturn(Optional.of(disneyWorld));
		mockMvc.perform(get("/api/properties/1"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.propertyAddress").value("DisneyWorld"));
	}

	@Test
	void findByIdIfDoesNotExist() throws Exception {
		Property disneyUniverse = new Property("DisneyUniverse", 20, 4000);
		given(propertyRepository.findById(1111)).willReturn(Optional.empty());
		mockMvc.perform(get("/api/properties/1111"))
				.andExpect(status().isNotFound());
	}

	@Test
	void givenUserIsAnonymousWhenDeleteThenIsUnauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/properties/{eircode}", 1)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username="manager",roles={"MNGR"})
	void givenUserIsManagerWhenDeleteThenNoContent() throws Exception {
		when(propertyRepository.existsById(1)).thenReturn(true);
		mockMvc.perform( MockMvcRequestBuilders
				.delete("/api/properties/{eircode}", 1)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser
	void givenUserIsNotManagerWhenDeleteThenForbidden() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
				.delete("/api/properties/{eircode}", 1)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username="manager", roles={"MNGR"})
	void givenUserIsManagerWhenDeleteThenNotFound() throws Exception {
		when(propertyRepository.existsById(1)).thenReturn(false);
		mockMvc.perform( MockMvcRequestBuilders
					.delete("/api/properties/{eircode}", 1)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username="staff",roles={"STAFF"})
	void DeleteWhenUserIsStaffAndTenantExists() throws Exception {
		when(tenantRepository.existsById(1)).thenReturn(true);
		mockMvc.perform( MockMvcRequestBuilders
						.delete("/api/tenants/{id}", 1)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username="staff",roles={"STAFF"})
	void DeleteWhenUserIsStaffAndTenantDoesNotExist() throws Exception {
		when(tenantRepository.existsById(1)).thenReturn(false);
		mockMvc.perform( MockMvcRequestBuilders
						.delete("/api/tenants/{id}", 7)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser
	void DeleteWhenUserIsNotStaffAndTenantExists() throws Exception {
		when(tenantRepository.existsById(1)).thenReturn(true);
		mockMvc.perform( MockMvcRequestBuilders
						.delete("/api/tenants/{email}", 1)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}


	@Test
	@WithMockUser(username="staff", roles={"STAFF"})
	void givenUserIsStaffButNotManagerWhenDeletePropertiesThenForbidden() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
						.delete("/api/properties/{eircode}", 1)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username="staff",roles={"STAFF"})
	void givenUserIsStaffWhenUpdatePropertiesThenForbidden() throws Exception {
		when(propertyRepository.existsById(1)).thenReturn(true);
		mockMvc.perform( MockMvcRequestBuilders
						.patch("/api/properties/{eircode}/rent", 1)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
}
