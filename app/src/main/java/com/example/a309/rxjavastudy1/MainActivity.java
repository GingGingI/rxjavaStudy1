package com.example.a309.rxjavastudy1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText dan,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1번째

        tv = findViewById(R.id.helloView);
        Observable.just(tv.getText().toString())//input tv에서 getString 을 해와서 리턴
        .map(s -> s + "With Rx!") //operators s라는 파라미터로 gettext를가져와서 s += " With Rx!" 을 리턴
        .subscribe(text -> tv.setText(text));//output text로 return을 받아와서 tv에다가 적용

        dan = findViewById(R.id.Dan);
        result = findViewById(R.id.Result);

        findViewById(R.id.printBtn).setOnClickListener(view -> {
            /** 옛코드
            int ddan = Integer.parseInt(dan.getText().toString());
            Observable.range(1, 9)
                    .map(row -> {
                            if (row == 1) result.setText(""); //row 가 1이면 일단 result를 초기화
                            return ddan + " * " + row + " = " + (ddan * row); // 평소같이 구구단리턴
                    })
                    .map(row -> row + "\n") //리턴받은거 \n함
                    .subscribe(result::append,// 리턴받은것을 append함 text -> result.append(text)랑 같음
                            e -> Toast.makeText(this, "값 입력 잘못함", Toast.LENGTH_SHORT).show()); //예외가 발생하면 Toast출력
        });
             */
            Observable.just(dan.getText().toString()) //string 리턴
                    .map(ddan -> Integer.parseInt(ddan)) // ddan이란이름으로 return을받아온 뒤 parseInt를 한후 출력 // int 리턴
                    .filter(ddan -> 1 < ddan && ddan < 10) //ddan으로 받아와 1 < ddan > 10 을 해봄 통과면 그대로return 아니면 그대로 동작그만 // int 리턴
                    .flatMap(ddan -> Observable.range(1,9), //return 받은ddan 에 함수실행 / 1~9까지 반복문실행
                            (ddan, row) -> ddan + " * "  + row + " = " + (ddan * row)) //ddan으로 return을받아오고 row는 반복문의 i 같은역할 그리고 String 리턴
                    .map(row -> row + "\n") // row 에 row+\n 하여String 리턴
                    .subscribe(
                            result::append, //리턴받은거 append함
                            e -> Toast.makeText(this,
                                    "구구단은 2단아니면 9단까지밖에못해용",
                                    Toast.LENGTH_SHORT).show()//에러가터지면 토스트발생.
                    );
        });
    }
}
