package com.pfefferminzia.unfall.web;

import com.pfefferminzia.unfall.domain.Contract;
import com.pfefferminzia.unfall.service.TariffService;
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

  @PostMapping
  public ResponseEntity<Contract> create(@RequestBody Contract contract) {
    Contract saved = tariffService.createAndSave(contract);
    URI location = URI.create("/api/contracts/" + saved.getId());
    return ResponseEntity.created(location).body(saved);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Contract> get(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(tariffService.getById(id));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Contract>> list() {
    List<Contract> all = tariffService.listAll();
    if (all.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(all);
  }

  @PostMapping("/{id}/calculate")
  public ResponseEntity<Contract> recalc(@PathVariable Long id) {
    try {
      Contract updated = tariffService.recalculateAndSave(id);
      return ResponseEntity.ok(updated);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
