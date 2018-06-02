package com.dlamloi.MAD.taskcreation;

import com.dlamloi.MAD.meetingcreation.CreateMeetingPresenter;
import com.dlamloi.MAD.model.Task;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.utilities.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Don on 17/05/2018.
 */

public class CreateTaskPresenter implements CreateTaskContract.Presenter {

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_COMPLETE = "Complete";

    private final CreateTaskContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;


    public CreateTaskPresenter(CreateTaskContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
        mFirebaseRepositoryManager.getUsers(this);

    }

    @Override
    public void taskDateClicked() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        mView.showDateDialog(year, month, day);
    }

    @Override
    public void datePicked(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(CreateMeetingPresenter.DATE_PATTERN);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = dateFormat.format(calendar.getTime());
        mView.setDueDate(date);
    }

    @Override
    public void assignTask(String assignedMember, String taskTitle, String dueDate, String taskDescription) {
        String[] assignedMemberSplit = assignedMember.split("-");
        String assignMemberEmail = assignedMemberSplit[1].trim();
        String assignMemberDisplayName = assignedMemberSplit[0].trim();
        Task task = new Task(taskTitle, taskDescription, STATUS_PENDING, assignMemberEmail, assignMemberDisplayName, dueDate);
        mFirebaseRepositoryManager.addTask(task);
        mView.leave();
    }

    @Override
    public void addSpinnerData(ArrayList<String> displayNames) {
        mView.setUpSpinnerData(displayNames);
    }

    @Override
    public void shouldAssignBeEnabled(String selectedItem, String title, String duedate) {
        if (Utility.areAnyRequiredFieldsEmpty(selectedItem, title, duedate)) {
            mView.disableAssignButton();
        } else {
            mView.enableAssignButton();
        }
    }

    @Override
    public void homeButtonPressed(String title, String dueDate) {
        if (!title.isEmpty() || !dueDate.isEmpty()) {
            mView.showAlertDialog();
        } else {
            mView.leave();
        }
    }
}
