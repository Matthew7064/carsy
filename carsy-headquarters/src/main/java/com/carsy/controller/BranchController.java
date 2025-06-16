package com.carsy.controller;

import com.carsy.model.Branch;
import com.carsy.repository.BranchRepository;
import com.carsy.service.BranchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/branches")
public class BranchController {
    private final BranchService branchService;
    private final BranchRepository branchRepository;

    @Autowired
    public BranchController(BranchService branchService, BranchRepository branchRepository) {
        this.branchService = branchService;
        this.branchRepository = branchRepository;
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.findAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable("id") UUID id) {
        Branch branch = branchService.findBranch(id);
        if (branch != null) return ResponseEntity.ok(branch);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Branch> addBranch(@RequestBody @Valid Branch branch) {
        branchService.addBranch(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(branch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> editBranch(@PathVariable("id") UUID id, @RequestBody @Valid Branch branch) {
        Branch updatedBranch = branchService.editBranch(branch, id);
        if (updatedBranch != null) return ResponseEntity.ok(updatedBranch);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable("id") UUID id, @RequestBody @Valid Branch branch) {
        Branch updatedBranch = branchService.updateBranch(branch, id);
        if (updatedBranch != null) return ResponseEntity.ok(updatedBranch);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBranch(@PathVariable("id") UUID id) {
        branchService.removeBranch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-url")
    public ResponseEntity<UUID> getBranchIdByUrl(HttpServletRequest request) {
        String url = ServletRequestUtils.getStringParameter(request, "url", "");
        if (!url.trim().isBlank()) {
            Branch branch = branchRepository.findByUrl(url);
            if (branch != null) {
                return ResponseEntity.ok(branch.getId());
            }
        }
        return ResponseEntity.notFound().build();
    }
}
