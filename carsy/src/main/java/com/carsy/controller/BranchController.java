package com.carsy.controller;

import com.carsy.model.Branch;
import com.carsy.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/branches")
public class BranchController {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.findAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable("id") Long id) {
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
    public ResponseEntity<Branch> editBranch(@PathVariable("id") Long id, @RequestBody @Valid Branch branch) {
        Branch updatedBranch = branchService.editBranch(branch, id);
        if (updatedBranch != null) return ResponseEntity.ok(updatedBranch);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable("id") Long id, @RequestBody @Valid Branch branch) {
        Branch updatedBranch = branchService.updateBranch(branch, id);
        if (updatedBranch != null) return ResponseEntity.ok(updatedBranch);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBranch(@PathVariable("id") Long id) {
        branchService.removeBranch(id);
        return ResponseEntity.noContent().build();
    }
}
