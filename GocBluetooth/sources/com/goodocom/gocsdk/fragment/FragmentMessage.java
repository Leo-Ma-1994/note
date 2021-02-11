package com.goodocom.gocsdk.fragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.event.MessageListEvent;
import com.goodocom.gocsdk.event.MessageTextEvent;
import com.goodocom.gocsdk.service.GocsdkCallbackImp;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentMessage extends BaseFragment implements View.OnClickListener {
    private MainActivity activity;
    private Button btn_detail_return;
    private Button btn_message_in;
    private Button btn_message_out;
    private Button btn_message_rubbish;
    private Button btn_other;
    private Button btn_return;
    private Button btn_select_contact;
    private Button btn_send;
    private Button btn_write_message;
    private MyDelAdapter delAdapter;
    private List<MessageListEvent> delMessages = new ArrayList();
    private EditText et_addressee;
    private EditText et_message_content;
    private MyInAdapter inAdapter;
    private List<MessageListEvent> inMessages = new ArrayList();
    private ListView lv_message_content;
    private ListView lv_message_in;
    private ListView lv_rubbish_message;
    private ListView lv_select_contact;
    private String message_content;
    private MyOutAdapter outAdapter;
    private List<MessageListEvent> outMessages = new ArrayList();
    private int pager = 0;
    private RelativeLayout rl_message_detail_content;
    private RelativeLayout rl_message_out;
    private RelativeLayout rl_write_message;
    private TextView tv_addressee_name;
    private TextView tv_addressee_number;
    private TextView tv_message_detail_content;

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(this.activity, R.layout.fragmentmessage, null);
        EventBus.getDefault().register(this);
        this.btn_message_in = (Button) view.findViewById(R.id.btn_message_in);
        this.btn_message_out = (Button) view.findViewById(R.id.btn_message_out);
        this.btn_message_rubbish = (Button) view.findViewById(R.id.btn_message_rubbish);
        this.lv_message_in = (ListView) view.findViewById(R.id.lv_message_in);
        this.rl_message_out = (RelativeLayout) view.findViewById(R.id.rl_message_out);
        this.btn_write_message = (Button) view.findViewById(R.id.btn_write_message);
        this.lv_message_content = (ListView) view.findViewById(R.id.lv_message_content);
        this.rl_write_message = (RelativeLayout) view.findViewById(R.id.rl_write_message);
        this.btn_return = (Button) view.findViewById(R.id.btn_return);
        this.et_addressee = (EditText) view.findViewById(R.id.et_addressee);
        this.btn_select_contact = (Button) view.findViewById(R.id.btn_select_contact);
        this.et_message_content = (EditText) view.findViewById(R.id.et_message_content);
        this.btn_send = (Button) view.findViewById(R.id.btn_send);
        this.lv_select_contact = (ListView) view.findViewById(R.id.lv_select_contact);
        this.rl_message_detail_content = (RelativeLayout) view.findViewById(R.id.rl_message_detail_content);
        this.btn_detail_return = (Button) view.findViewById(R.id.btn_detail_return);
        this.tv_addressee_name = (TextView) view.findViewById(R.id.tv_addressee_name);
        this.tv_addressee_number = (TextView) view.findViewById(R.id.tv_addressee_number);
        this.btn_other = (Button) view.findViewById(R.id.btn_other);
        this.tv_message_detail_content = (TextView) view.findViewById(R.id.tv_message_detail_content);
        this.lv_rubbish_message = (ListView) view.findViewById(R.id.lv_rubbish_message);
        this.btn_message_in.setOnClickListener(this);
        this.btn_message_out.setOnClickListener(this);
        this.btn_message_rubbish.setOnClickListener(this);
        this.btn_write_message.setOnClickListener(this);
        this.btn_return.setOnClickListener(this);
        this.btn_select_contact.setOnClickListener(this);
        this.btn_send.setOnClickListener(this);
        this.btn_detail_return.setOnClickListener(this);
        if (GocsdkCallbackImp.hfpStatus <= 0) {
            Toast.makeText(this.activity, "请您先连接设备", 0).show();
        }
        this.inAdapter = new MyInAdapter();
        this.outAdapter = new MyOutAdapter();
        this.delAdapter = new MyDelAdapter();
        this.lv_message_in.setAdapter((ListAdapter) this.inAdapter);
        this.lv_message_in.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentMessage.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FragmentMessage.this.showDetailMessage(FragmentMessage.this.inAdapter.getItem(position));
            }
        });
        this.lv_message_content.setAdapter((ListAdapter) this.outAdapter);
        this.lv_message_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentMessage.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FragmentMessage.this.showDetailMessage(FragmentMessage.this.outAdapter.getItem(position));
            }
        });
        this.lv_rubbish_message.setAdapter((ListAdapter) this.delAdapter);
        this.lv_rubbish_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentMessage.AnonymousClass3 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FragmentMessage.this.showDetailMessage(FragmentMessage.this.delAdapter.getItem(position));
            }
        });
        return view;
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageTextEvent content) {
        this.message_content = content.text;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageListEvent info) {
        int i = this.pager;
        if (i == 0) {
            this.inMessages.add(info);
        } else if (i == 1) {
            this.outMessages.add(info);
        } else if (i == 2) {
            this.delMessages.add(info);
        }
    }

    /* access modifiers changed from: protected */
    public void showDetailMessage(MessageListEvent messageInfo) {
        this.lv_message_in.setVisibility(8);
        this.rl_message_out.setVisibility(8);
        this.rl_write_message.setVisibility(8);
        this.lv_select_contact.setVisibility(8);
        this.rl_message_detail_content.setVisibility(0);
        this.lv_rubbish_message.setVisibility(8);
        this.tv_addressee_name.setText(messageInfo.name);
        this.tv_addressee_number.setText(messageInfo.num);
        this.tv_message_detail_content.setText(this.message_content);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detail_return /* 2131165244 */:
                int i = this.pager;
                if (i == 0) {
                    showInMessage();
                    return;
                } else if (i == 1) {
                    showOutMessage();
                    return;
                } else if (i == 2) {
                    showRubbishMessage();
                    return;
                } else {
                    return;
                }
            case R.id.btn_message_in /* 2131165246 */:
                if (GocsdkCallbackImp.hfpStatus > 0) {
                    List<MessageListEvent> list = this.inMessages;
                    if (list != null) {
                        list.clear();
                    }
                } else {
                    Toast.makeText(this.activity, "请您先连接设备", 0).show();
                }
                showInMessage();
                return;
            case R.id.btn_message_out /* 2131165247 */:
            case R.id.btn_message_rubbish /* 2131165248 */:
            case R.id.btn_send /* 2131165255 */:
            default:
                return;
            case R.id.btn_return /* 2131165253 */:
                showOutMessage();
                return;
            case R.id.btn_select_contact /* 2131165254 */:
                showContactList();
                return;
            case R.id.btn_write_message /* 2131165259 */:
                showWriteMessage();
                return;
        }
    }

    private void showContactList() {
        this.lv_message_in.setVisibility(8);
        this.rl_message_out.setVisibility(8);
        this.rl_write_message.setVisibility(8);
        this.lv_select_contact.setVisibility(0);
        this.rl_message_detail_content.setVisibility(8);
        this.lv_rubbish_message.setVisibility(8);
    }

    private void showWriteMessage() {
        this.lv_message_in.setVisibility(8);
        this.rl_message_out.setVisibility(8);
        this.rl_write_message.setVisibility(0);
        this.lv_select_contact.setVisibility(8);
        this.rl_message_detail_content.setVisibility(8);
        this.lv_rubbish_message.setVisibility(8);
    }

    private void showRubbishMessage() {
        this.pager = 2;
        this.lv_rubbish_message.setVisibility(0);
        this.rl_message_out.setVisibility(8);
        this.rl_write_message.setVisibility(8);
        this.lv_select_contact.setVisibility(8);
        this.rl_message_detail_content.setVisibility(8);
        this.lv_message_in.setVisibility(8);
    }

    private void showOutMessage() {
        this.pager = 1;
        this.lv_message_in.setVisibility(8);
        this.rl_message_out.setVisibility(0);
        this.rl_write_message.setVisibility(8);
        this.lv_select_contact.setVisibility(8);
        this.rl_message_detail_content.setVisibility(8);
        this.lv_rubbish_message.setVisibility(8);
    }

    private void showInMessage() {
        this.pager = 0;
        this.lv_rubbish_message.setVisibility(8);
        this.rl_message_out.setVisibility(8);
        this.rl_write_message.setVisibility(8);
        this.lv_select_contact.setVisibility(8);
        this.rl_message_detail_content.setVisibility(8);
        this.lv_message_in.setVisibility(0);
    }

    public class MyInAdapter extends BaseAdapter {
        public MyInAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FragmentMessage.this.inMessages.size();
        }

        @Override // android.widget.Adapter
        public MessageListEvent getItem(int position) {
            return (MessageListEvent) FragmentMessage.this.inMessages.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return (long) position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(FragmentMessage.this.activity, R.layout.message_item_layout, null);
                holder.tv_addressee = (TextView) convertView.findViewById(R.id.tv_addressee);
                holder.tv_sendOrReceiverTime = (TextView) convertView.findViewById(R.id.tv_sendorreceiver_time);
                holder.tv_message_content = (TextView) convertView.findViewById(R.id.tv_message_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            MessageListEvent messageInfo = (MessageListEvent) FragmentMessage.this.inMessages.get(position);
            holder.tv_addressee.setText(messageInfo.name);
            holder.tv_sendOrReceiverTime.setText(messageInfo.time);
            holder.tv_message_content.setText(messageInfo.title);
            return convertView;
        }

        public class ViewHolder {
            public TextView tv_addressee;
            public TextView tv_message_content;
            public TextView tv_sendOrReceiverTime;

            public ViewHolder() {
            }
        }
    }

    public class MyOutAdapter extends BaseAdapter {
        public MyOutAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FragmentMessage.this.outMessages.size();
        }

        @Override // android.widget.Adapter
        public MessageListEvent getItem(int position) {
            return (MessageListEvent) FragmentMessage.this.outMessages.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return (long) position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(FragmentMessage.this.activity, R.layout.message_item_layout, null);
                holder.tv_addressee = (TextView) convertView.findViewById(R.id.tv_addressee);
                holder.tv_sendOrReceiverTime = (TextView) convertView.findViewById(R.id.tv_sendorreceiver_time);
                holder.tv_message_content = (TextView) convertView.findViewById(R.id.tv_message_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            MessageListEvent messageInfo = (MessageListEvent) FragmentMessage.this.outMessages.get(position);
            holder.tv_addressee.setText(messageInfo.name);
            holder.tv_sendOrReceiverTime.setText(messageInfo.time);
            holder.tv_message_content.setText(messageInfo.title);
            return convertView;
        }

        public class ViewHolder {
            public TextView tv_addressee;
            public TextView tv_message_content;
            public TextView tv_sendOrReceiverTime;

            public ViewHolder() {
            }
        }
    }

    public class MyDelAdapter extends BaseAdapter {
        public MyDelAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FragmentMessage.this.delMessages.size();
        }

        @Override // android.widget.Adapter
        public MessageListEvent getItem(int position) {
            return (MessageListEvent) FragmentMessage.this.delMessages.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return (long) position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(FragmentMessage.this.activity, R.layout.message_item_layout, null);
                holder.tv_addressee = (TextView) convertView.findViewById(R.id.tv_addressee);
                holder.tv_sendOrReceiverTime = (TextView) convertView.findViewById(R.id.tv_sendorreceiver_time);
                holder.tv_message_content = (TextView) convertView.findViewById(R.id.tv_message_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            MessageListEvent messageInfo = (MessageListEvent) FragmentMessage.this.delMessages.get(position);
            holder.tv_addressee.setText(messageInfo.name);
            holder.tv_sendOrReceiverTime.setText(messageInfo.time);
            holder.tv_message_content.setText(messageInfo.title);
            return convertView;
        }

        public class ViewHolder {
            public TextView tv_addressee;
            public TextView tv_message_content;
            public TextView tv_sendOrReceiverTime;

            public ViewHolder() {
            }
        }
    }
}
