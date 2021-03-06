package com.dlamloi.MAD.home.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.home.GroupHomeActivity;
import com.dlamloi.MAD.model.ChatMessage;

import java.util.ArrayList;

/**
 * This class is responsible for showing the message tab
 */
public class MessagesFragment extends Fragment implements MessageContract.View {

    private String mGroupId;
    private ArrayList<ChatMessage> mChatMessages = new ArrayList<>();

    private RecyclerView mMessagesRv;
    private EditText mMessageEt;
    private Button mSendMessageBtn;
    private MessagePresenter mMessagePresenter;
    private MessageAdapter mMessageAdapter;

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the message fragment
     *
     * @param groupId the group id the user is current in
     * @return an instance of the messages fragment
     */
    public static MessagesFragment newInstance(String groupId) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(GroupHomeActivity.GROUP_KEY, groupId);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupId = getArguments().getString(GroupHomeActivity.GROUP_KEY);
        }
        mChatMessages = new ArrayList<>();
        mMessagePresenter = new MessagePresenter(this, mGroupId);
        mMessageAdapter = new MessageAdapter(mChatMessages);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mMessagesRv = view.findViewById(R.id.messages_recyclerview);
        mMessagesRv.setAdapter(mMessageAdapter);
        mMessagesRv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mMessagesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        mMessageEt = view.findViewById(R.id.message_edittext);
        mSendMessageBtn = view.findViewById(R.id.send_message_button);
        mSendMessageBtn.setOnClickListener(v -> mMessagePresenter.sendMessage(mMessageEt.getText().toString()));
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearMessageBox() {
        mMessageEt.getText().clear();
    }

    @Override
    public void populateRecyclerView(ArrayList<ChatMessage> chatMessages) {
        if (!mChatMessages.isEmpty()) {
            mChatMessages.clear();
        }
        mChatMessages.addAll(chatMessages);
        notifyItemInserted(chatMessages.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyItemInserted(int position) {
        mMessageAdapter.notifyItemInserted(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideFab() {
        ((GroupHomeActivity) getActivity()).hideFab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showFab() {
        ((GroupHomeActivity) getActivity()).showFab();

    }


}
