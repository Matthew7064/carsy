package com.carsy.service;

import com.carsy.model.Branch;

import java.util.List;

public interface BranchService {
    void addBranch(Branch branch);
    Branch editBranch(Branch branch, long id);
    Branch updateBranch(Branch branch, long id);
    List<Branch> findAllBranches();
    void removeBranch(long id);
    Branch findBranch(long id);
}
