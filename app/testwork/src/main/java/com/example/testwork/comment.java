package com.example.testwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class comment extends AppCompatActivity {

    private Button btnAddComment;
    private TextView tvComments;

    private List<String> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 初始化评论列表
        commentsList = new ArrayList<>();

        // 查找视图组件
        btnAddComment = findViewById(R.id.btnAddComment);
        tvComments = findViewById(R.id.tvComments);

        // 设置按钮点击事件监听器
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCommentDialog();
            }
        });
    }

    // 显示添加评论对话框
    private void showAddCommentDialog() {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加评论");

        // 加载自定义布局
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_comment, null);
        builder.setView(dialogView);

        // 查找对话框中的输入框
        final EditText etComment = dialogView.findViewById(R.id.etComment);

        // 设置对话框按钮和点击事件
        builder.setPositiveButton("发表", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取用户输入的评论内容
                String comment = etComment.getText().toString();

                // 将评论添加到列表中
                commentsList.add(comment);

                // 更新评论显示区域
                updateCommentDisplay();
            }
        });

        builder.setNegativeButton("取消", null);

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 更新评论显示区域
    private void updateCommentDisplay() {
        StringBuilder sb = new StringBuilder();
        for (String comment : commentsList) {
            sb.append(comment).append("\n");
        }
        tvComments.setText(sb.toString());
    }
}
