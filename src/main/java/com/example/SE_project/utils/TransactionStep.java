package com.example.SE_project.utils;

public interface TransactionStep{
    void doActions() throws Exception;
    void undoActions() throws Exception;
}
