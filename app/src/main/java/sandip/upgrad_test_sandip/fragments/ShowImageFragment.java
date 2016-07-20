package sandip.upgrad_test_sandip.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import sandip.upgrad_task.R;


public class ShowImageFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	ImageView iv_content;
	String Url;

	int resource;


	public static ShowImageFragment newInstance(String Url, boolean is_show_buttons) {
		ShowImageFragment fragment = new ShowImageFragment();
		fragment.Url = Url;

		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_show_image, container,
				false);
		iv_content = (ImageView) view.findViewById(R.id.iv_content);

		Picasso.with(getActivity())
				.load(Url)
				.placeholder(R.mipmap.ic_launcher)
				.error(R.mipmap.ic_launcher)
				.into(iv_content);


		return view;
		}




@Override
public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
		}

		}
