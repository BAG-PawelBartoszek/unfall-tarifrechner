package com.pfefferminzia.unfall.web;

import com.pfefferminzia.unfall.domain.Contract;
import com.pfefferminzia.unfall.service.TariffService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "*")
public class ContractController {

    private final TariffService tariffService;

    public ContractController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all contracts", description = "Retrieve all contracts")
    @ApiResponse(
            responseCode = "200",
            description = "Contracts found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Contract.class))
            )
    )
    @ApiResponse(
            responseCode = "204",
            description = "No contracts found",
            content = @Content
    )
    public ResponseEntity<List<Contract>> listAll() {
        List<Contract> all = tariffService.listAll();
        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a contract by ID", description = "Retrieve a specific contract using its unique identifier")
    @ApiResponse(
            responseCode = "200",
            description = "Contract found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Contract.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Contract not found",
            content = @Content
    )
    public ResponseEntity<Contract> getById(@Parameter(description = "Unique identifier of the contract", required = true, example = "1")
                                            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(tariffService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new contract",
            description = "Create a new contract and save it in the database"
    )
    @ApiResponse(responseCode = "201", description = "Contract created")
    public ResponseEntity<Contract> create(@RequestBody Contract contract) {
        Contract saved = tariffService.createAndSave(contract);
        URI location = URI.create("/api/contracts/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping(path = "/{id}/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update a contract by ID",
            description = "Retrieve a specific contract using its unique identifier, update it and save it in the database")
    @ApiResponse(
            responseCode = "200",
            description = "Contract updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Contract.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Contract not found",
            content = @Content
    )
    public ResponseEntity<Contract> update(@PathVariable Long id) {
        try {
            Contract updated = tariffService.recalculateAndSave(id);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
