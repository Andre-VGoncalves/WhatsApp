package andre.com.whatsapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import andre.com.whatsapp.fragment.ContatosFragment;
import andre.com.whatsapp.fragment.ConversasFragment;

/**
 * Created by andrevieira on 11/01/2018.
 */

public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] tituloAbas = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new ConversasFragment();
                break;
            case 1:
                fragment = new ContatosFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
