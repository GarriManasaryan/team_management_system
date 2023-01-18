# Functional requirements

## Overview

Idea: allow team leads to manage the department, such as:
* CUD teams;
* CUD employees;
* CUD employee responsibilities: projects, specialization (analysts can have professional knowledge in certain spheres) and so on;
* CUD employee skills;
* plan employees capacities: how many reports a certain employee can handle this week + time management, for example, set days off, meeting hours etc.;

## Manager actions and requirements

### CUD teams and employees

_Use-cases_:
* Manager clicks on **Teams** section and creates a new team (e.g. hematology team);
* Manager clicks on **Teams** section, chooses a team and renames it (e.g. hem+reproductive team);
* Manager clicks on **Teams** section, chooses a team and deletes it;
* Inside teams click Add/Remove/Update employee;

An employee should have several mandatory attributes, like telegram_username, <another_outer_database_employee_works> id etc.

### CUD employee responsibilities

_Use-cases_:
* Manager chooses an employee from the **Teams** section and CUDs responsibilities;
"Responsibility" is a broad term. For instance, clinical team can have responsibilities in projects, specializations, "chores" (like birthday and activity plannings) and so on.
So we need to add scopes - types of responsibilities:
* projects: free text;
* specializations: free text or integrated from another database (for example, diagnoses or treatment therapy classes, that analyst has expert knowledge);
* chores: free text;
* micro-sections: for example, this employee will be code reviewing or some minor (and repeating) tasks;
* others: unclassified responsibilities.

All responsibilities will have a common interface and attributes, like name, type and label (in projects, for example, managers can add a specific short project label for navigation).

### CUD employee skills

"Skill" is another broad term, which is a little harder to classify for now. To keep it simple, this field will be a free-text.

### Plan capacity

_Use-cases_:
Managers:
* choose a planning timeline: days, weeks or months;
* set working hours and days for employees and teams;
* set capacities: orders, projects per hours;

For example:
→ Time Off
→ Planned Meeting Load
→ Expected Meeting Load
→ Annotations time
→ Content Time with Hard Deadlines
→ Safety Time
→ QC Time

Capacity can include orders, projects, meetings and so on.

* set weekends shifts and manage vacations: substitutions and else;

## Employees actions and requirements

### CUD tasks 

_Use-cases_:
* users can add tasks with priorities, labels, sub-tasks, completion status and so on;

## General actions

### Display employee and team analytics

_Use-cases_:
* users click to personal account and view custom analytics: most common drugs, diagnoses etc.

### Projects news

_Use-cases_:
* managers post project news;

### Leave feedback

_Use-cases_:
* users can leave feedback for later discussions

### Store and visualize data flow (from databases to product)

This tool allows to visualize data transfer from database to final product (through all ETLs, intermediate scripts etc.), which will help developers to quickly identify the problem.  
_Use-cases_:
* managers (or project leads) can update data flow by changing databases or involved scripts;
* users can search entities, like treatment status or diagnoses and view, where data originates and what processes are manipulating with it; 

Here we can identify different data states (types):
* database (storage);
* ETL (scripts);
* final product;
* intermediate google sheet (storage);
* analysts' working tool (where they select data, combine and prepare for final product);