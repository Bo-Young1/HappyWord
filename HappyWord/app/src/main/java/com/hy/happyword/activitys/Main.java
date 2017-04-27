package com.hy.happyword.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hy.happyword.R;
import com.hy.happyword.business.OperationOfBooks;
import com.hy.happyword.database.DataAccess;
import com.hy.happyword.database.SqlHelper;
import com.hy.happyword.model.BookList;
import com.hy.happyword.model.Word;
import com.hy.happyword.model.WordList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by 980559 on 2017/4/26.
 */
public class Main extends AppCompatActivity implements View.OnClickListener{
    public static String SETTING_BOOKID = "bookID";
    public static String BOOKNAME = "BOOKNAME";
    private Spinner pickBook;
    private TextView info;
    private ImageButton deleteBu;
    private ImageButton resetBu;
    private Button learnBu;
    private Button reviewBu;
    private Button testBu;
    private Button attentionBu;
    private ProgressBar learn_progress;
    private ProgressBar review_progress;
    private TextView learn_text;
    private TextView review_text;
    public static final int MENU_SETTING = 1;
    public static final int MENU_ABOUT = MENU_SETTING + 1;
    public static final int MENU_CONTACT = MENU_SETTING + 2;
    View myView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.setTitle("安卓开心词场APP");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login);
        LayoutInflater mInflater = LayoutInflater.from(this);
        myView = mInflater.inflate(R.layout.main,null);
        Thread thread = new Thread(){
            @Override
            public void run() {
               try{
                   Thread.sleep(2000);
                   Message m = new Message();
                   m.what = 1;
                   Main.this.mHandler.sendMessage(m);
               }catch (InterruptedException e1){
                   e1.printStackTrace();
               }
            }
        };
        thread.start();

        OperationOfBooks OOB = new OperationOfBooks();
        SharedPreferences setting = getSharedPreferences("wordroid.model_preferences",MODE_PRIVATE);
        OOB.setNotify(setting.getString("time","18:00 下午"),Main.this);
        File dir = new File("data/data/wordroid.model/databases");
        if (!dir.exists())
            dir.mkdir();
        if (!(new File(SqlHelper.DB_NAME)).exists()){
            FileOutputStream fos;
            try{
                fos = new FileOutputStream(SqlHelper.DB_NAME);
                byte[] buffer = new byte[8192];
                int count = 0;
                InputStream is = getResources().openRawResource(R.raw.wordorid);
                while ((count = is.read(buffer)) > 0){
                    fos.write(buffer,0,count);
                }
                fos.close();
                is.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        SharedPreferences settings = getSharedPreferences(SETTING_BOOKID,0);
        DataAccess.bookID = settings.getString(BOOKNAME,"");
        OOB.UpdateListInfo(Main.this);
        initWidgets();
    }

    private void initSpinner(){
        DataAccess data = new DataAccess(this);
        final ArrayList<BookList> bookList = data.QueryBook(null,null);
        String[] books = new String[bookList.size()+1];
        int i=0;
        for (;i<bookList.size();i++){
            books[i] = bookList.get(i).getName();
        }
        books[i] = "导入新词库";
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,books);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickBook.setAdapter(adapter);
        pickBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < bookList.size()){
                    DataAccess.bookID = bookList.get(position).getID();
                    info.setText("\n词库名称：\n   "+bookList.get(position).getName()
                            +"\n总词汇量：\n   "+bookList.get(position).getNumOfWord()
                            +"\n创建时间：\n    "+bookList.get(position).getGenerateTime());
                    deleteBu.setEnabled(true);
                    learnBu.setEnabled(true);
                    resetBu.setEnabled(true);
                    reviewBu.setEnabled(true);
                    testBu.setEnabled(true);
                    DataAccess data2 = new DataAccess(Main.this);
                    ArrayList<WordList> lists = data2.QueryList("BOOKID ='"+DataAccess.bookID+"'",null);
                    learn_progress.setMax(lists.size());
                    review_progress.setMax(lists.size());
                    learn_progress.setProgress(0);
                    review_progress.setProgress(0);
                    review_progress.setSecondaryProgress(0);
                    int learned = 0,reviewed = 0;
                    for (int k=0;k<lists.size();k++){
                        if (lists.get(k).getLearned().equals("1")){
                            learn_progress.incrementProgressBy(1);
                            learned++;
                        }
                        if (Integer.parseInt(lists.get(k).getReview_times())>=5){
                            review_progress.incrementProgressBy(1);
                            reviewed++;
                        }
                        if (Integer.parseInt(lists.get(k).getReview_times())>=0)
                            review_progress.incrementSecondaryProgressBy(1);
                        review_text.setText("已复习"+reviewed+"/"+lists.size());
                        learn_text.setText("已学习"+learned+"/"+lists.size());
                    }
                }else if(position == bookList.size()){
                    Intent intent = new Intent();
                    intent.setClass(Main.this,ImportBook.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (bookList.size() == 0){
            pickBook.setSelection(1);
            info.setText("请先从上方选择一个词库！");
            this.deleteBu.setEnabled(false);
            this.learnBu.setEnabled(false);
            this.resetBu.setEnabled(false);
            this.reviewBu.setEnabled(false);
            this.testBu.setEnabled(false);
            this.learn_progress.setProgress(0);
            this.review_progress.setProgress(0);
            return;
        }
        int j=0;
        for (;j<bookList.size();j++){
            if (DataAccess.bookID.equals(bookList.get(j).getID())){
                pickBook.setSelection(j);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}











































