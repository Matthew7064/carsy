package com.carsy.service;

import com.carsy.model.Branch;

import java.util.List;
import java.util.UUID;

public interface BranchService {
    void addBranch(Branch branch);
    Branch editBranch(Branch branch, UUID id);
    Branch updateBranch(Branch branch, UUID id);
    List<Branch> findAllBranches();
    void removeBranch(UUID id);
    Branch findBranch(UUID id);
}
