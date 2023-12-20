package com.example.finalwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class comment extends AppCompatActivity {

    private TextView tvComments;

    private List<String> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 返回键
        TextView comment_back = findViewById(R.id.comment_back);
        // 评论显示区域 + 添加评论
        TextView addComment = findViewById(R.id.addComment);
        tvComments = findViewById(R.id.tvComments);
        // 初始化评论列表
        commentsList = new ArrayList<>();

        // 返回跳转
        comment_back.setOnClickListener(view -> {
            Intent to_page4 = new Intent(comment.this,page4.class);
            to_page4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_page4);
        });

        // 设置"点击评论"的监听
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCommentDialog();
            }
        });
    }

    // 获取当前时间的字符串表示
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    // 显示添加评论对话框
    private void showAddCommentDialog() {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加评论");

        // 加载自定义布局，以便将评论内容动态展示在页面上
        // 使用LayoutInflater从指定的布局文件实例化一个视图对象，并将其设置为构建器（builder）的视图
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_comment, null);
        builder.setView(dialogView);

        // 查找对话框中的输入框
        final EditText etComment = dialogView.findViewById(R.id.etComment);

        // 设置对话框 “发表” 的点击事件
        builder.setPositiveButton("发表", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentTime = getCurrentTime();                    // 获取当前时间
                String comment = etComment.getText().toString();          // 获取用户输入的评论内容
                commentsList.add(currentTime + " \n " + comment);         // 将 发表时间+评论 添加到列表中
                updateCommentDisplay();                                   // 更新评论显示区域（该函数在下方定义）
            }
        });

        // 对话框点击 “取消” 则关闭对话框，无需额外设置监听
        builder.setNegativeButton("取消", null);

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 更新评论显示区域，用于将列表中的评论信息写到xml页面上
    private void updateCommentDisplay() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        // 遍历列表，将其信息写到页面上
        for (String comment : commentsList) {
            // 找到第一个换行符的索引位置，利用它对每条评论文本的 时间、评论内容 进行分割，设置不同字体
            int separatorIndex = comment.indexOf(" \n ");
            String currentTime = comment.substring(0, separatorIndex);
            String displayText = comment.substring(separatorIndex + 3);
            SpannableString spannableString = new SpannableString(currentTime + " \n " + displayText);

            // 设置当前时间的文本样式（较小的字体）
            spannableString.setSpan(new RelativeSizeSpan(0.7f), 0, currentTime.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // 设置评论的文本样式（较大的字体）    起始位置为当前时间的长度加上 3
            spannableString.setSpan(new RelativeSizeSpan(1.2f), currentTime.length() + 3,
                    currentTime.length() + 3 + displayText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // 最后加上两个换行符，以便将不同的评论信息分开逐条显示，而不是堆在一起
            sb.append(spannableString).append("\n\n");
        }
        tvComments.setText(sb);   // 将最终的整个列表的内容，写入相应TextView控件中
    }
}
