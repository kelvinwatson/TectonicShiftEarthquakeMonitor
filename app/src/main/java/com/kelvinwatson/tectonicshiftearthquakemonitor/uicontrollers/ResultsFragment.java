package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kelvinwatson.tectonicshiftearthquakemonitor.BR;
import com.kelvinwatson.tectonicshiftearthquakemonitor.MyApp;
import com.kelvinwatson.tectonicshiftearthquakemonitor.R;
import com.kelvinwatson.tectonicshiftearthquakemonitor.databinding.EarthquakeRowBinding;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakeViewModel;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakesViewModel;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakesViewModel.ViewState;
import java.lang.ref.WeakReference;
import java.util.List;

public class ResultsFragment extends BaseFragment
{
    private EarthquakesViewModel viewModel;
    private RecyclerView recyclerView;
    private EarthquakesAdapter recyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initializeViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewDataBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false);
        binding.setVariable(BR.itemClickListener, this);
        binding.setVariable(BR.viewModel, viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //FIXME: separate concerns—move recyclerView into recyclerFragment
        recyclerView = view.findViewById(R.id.recycler_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recyclerAdapter = new EarthquakesAdapter(this));
    }

    @Override
    public boolean onItemClick(@NonNull final View view, @NonNull final ViewModel viewModel)
    {
        switch (view.getId())
        {
            case R.id.btn_reload:
                ((EarthquakesViewModel)viewModel).refreshData();
                return true;
        }
        return false;
    }

    private void initializeViewModel()
    {
        final Observer<List<EarthquakeViewModel>> earthquakeListObserver = new Observer<List<EarthquakeViewModel>>()
        {
            @Override
            public void onChanged(@Nullable final List<EarthquakeViewModel> earthquakes)
            {
                onEarthquakeListChanged(earthquakes);
            }
        };

        viewModel = ViewModelProviders.of(this).get(EarthquakesViewModel.class);
        viewModel.getData((MyApp)getActivity().getApplication(), "44.1", "9.9", "22.4", "55.2",
            getString(R.string.earthquakes_api_user))
            .observe(this, earthquakeListObserver);
    }

    /**
     * Update views based on data.
     */
    private void onEarthquakeListChanged(@Nullable List<EarthquakeViewModel> earthquakes)
    {
        if (earthquakes == null || earthquakes.isEmpty())
        {
            viewModel.setViewState(ViewState.EMPTY);
            return;
        }

        viewModel.setViewState(ViewState.READY);
        recyclerAdapter.setEarthquakes(earthquakes);
    }

    static class EarthquakesAdapter<E extends ItemClickListener>
        extends RecyclerView.Adapter<EarthquakesAdapter.ViewHolder>
    {
        private List<EarthquakeViewModel> earthquakes;
        private WeakReference<E> itemClickListener;

        public EarthquakesAdapter(E itemClickListener)
        {
            this.itemClickListener = new WeakReference<>(itemClickListener);
        }

        public void setEarthquakes(@Nullable List<EarthquakeViewModel> earthquakes)
        {
            this.earthquakes = earthquakes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EarthquakesAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            EarthquakeRowBinding itemBinding = EarthquakeRowBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(itemBinding, itemClickListener.get());
        }

        @Override
        public void onBindViewHolder(@NonNull final EarthquakesAdapter.ViewHolder holder, final int position)
        {
            holder.bind(earthquakes.get(position));
        }


        @Override
        public int getItemViewType(final int position)
        {
            return R.layout.earthquake_row;
        }

        @Override
        public int getItemCount()
        {
            return earthquakes == null ? 0 : earthquakes.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder
        {
            private final ItemClickListener itemClickListener;
            private final EarthquakeRowBinding dataBinding;

            public ViewHolder(EarthquakeRowBinding dataBinding, ItemClickListener itemClickListener)
            {
                super(dataBinding.getRoot());
                this.itemClickListener = itemClickListener;
                this.dataBinding = dataBinding;
            }

            public void bind(EarthquakeViewModel earthquake)
            {
                dataBinding.setItemClickListener(itemClickListener);
                dataBinding.setEarthquake(earthquake);
                dataBinding.executePendingBindings();
            }
        }
    }
}
