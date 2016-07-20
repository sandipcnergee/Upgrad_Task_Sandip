package sandip.upgrad_test_sandip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;

import sandip.upgrad_test_sandip.fragments.ShowImageFragment;


public class ShowImageAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private int mCount = 3;
	ArrayList<String> al_url=new ArrayList<>();

	public ShowImageAdapter(FragmentManager fm, ArrayList<String> al_url) {
		super(fm);
		this.al_url=al_url;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0)
			return ShowImageFragment.newInstance(al_url.get(0),false);
		else if (position == 1)
			return ShowImageFragment.newInstance(al_url.get(1),false);
		else if (position == 2)
			return ShowImageFragment.newInstance(al_url.get(2),true);

		else
			return null;

	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}