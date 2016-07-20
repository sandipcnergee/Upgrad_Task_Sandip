package sandip.upgrad_test_sandip.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sandip.upgrad_task.R;
import sandip.upgrad_test_sandip._interface.IError;
import sandip.upgrad_test_sandip._interface.IResponse;
import sandip.upgrad_test_sandip.utils.MyUtils;
import sandip.upgrad_test_sandip.widgets.MyTextView;

/**
 * Created by Sumit on 25/06/2016.
 */
public  class BaseActivity extends AppCompatActivity implements IError,IResponse {
    int i = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void displayErrorPage(String error, final String actual_error) {
        {
            // TODO Auto-generated method stub

            // TODO Auto-generated method stub
            MyUtils.l("Error", "view");
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.activity_error_page, null);

            i = 0;
            setContentView(view);
            MyTextView tv_app_down = (MyTextView) view.findViewById(R.id.tv_app_down);
            ImageView iv_cloud_error = (ImageView) view.findViewById(R.id.iv_cloud_error);

            if (error.equalsIgnoreCase("slow")) {
                tv_app_down.setText(getString(R.string.soap_timeout));
            }

            if (error.equalsIgnoreCase("soap")) {
                tv_app_down.setText(getString(R.string.soap_fault));
            }

            if (error.equalsIgnoreCase("error")) {
                tv_app_down.setText(getString(R.string.error));
            }

            MyTextView tv_go_back = (MyTextView) view.findViewById(R.id.tv_go_back);
            final TextView tv_actual_error = (TextView) view.findViewById(R.id.tv_actual_error);

            tv_go_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    BaseActivity.this.finish();
                }
            });

            iv_cloud_error.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (i == 1) {
                        tv_actual_error.setVisibility(View.VISIBLE);
                        tv_actual_error.setText(actual_error);
                    }
                    i++;
                }
            });
        }

    }

    @Override
    public void web_response(ArrayList<String> response, Context context) {
        MyUtils.l("BaseActivity","response:"+response.get(2).toString());

    }

    public boolean handle_response(ArrayList<String> al_response,IError iError,boolean is_show_error_page){
        if(al_response!=null){
            if(al_response.size()>0){
                if(al_response.get(0).equalsIgnoreCase("OK")){

                    if(al_response.get(1).equalsIgnoreCase("200")){
                        return true;
                    }
                    else{
                        if(is_show_error_page)
                            iError.displayErrorPage(al_response.get(0), al_response.get(2));
                        return false;
                    }
                }
                else{
                    if(is_show_error_page)
                        iError.displayErrorPage(al_response.get(0), al_response.get(2));
                    return false;
                }
            }
            else{
                if(is_show_error_page)
                    iError.displayErrorPage(al_response.get(0), al_response.get(2));
                return false;
            }
        }
        else{
            if(is_show_error_page)
                iError.displayErrorPage(al_response.get(0), al_response.get(2));
            return false;
        }
    }

}
