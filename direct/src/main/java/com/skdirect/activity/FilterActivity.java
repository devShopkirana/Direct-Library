package com.skdirect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.innovattic.rangeseekbar.RangeSeekBar;
import com.skdirect.R;
import com.skdirect.adapter.BrandsFilterAdapter;
import com.skdirect.adapter.CategoryFilterAdapter;
import com.skdirect.adapter.DiscountFilterAdapter;
import com.skdirect.adapter.FilterTypeAdapter;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityFilterBinding;
import com.skdirect.interfacee.FilterCategoryInterface;
import com.skdirect.interfacee.FilterTypeInterface;
import com.skdirect.model.FilterCategoryDetails;
import com.skdirect.model.MallMainPriceModel;
import com.skdirect.model.PostBrandModel;
import com.skdirect.utils.Constant;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class FilterActivity extends AppCompatActivity implements FilterTypeInterface, FilterCategoryInterface {
    ActivityFilterBinding mBinding;
    CategoryFilterAdapter filterCategoryAdapter;
    BrandsFilterAdapter filterBrandAdapter;
    DiscountFilterAdapter filterDiscountAdapter;
    private int maxprice = 17500, minprice = 1;
    private int skipCount = 0;
    private final int takeCount = 15;
    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private boolean loading = true;
    public DBHelper dbHelper;
    int categoryId = 0;
    String discount = null;
    private final ArrayList<FilterCategoryDetails> filterCateDataList = new ArrayList<>();
    private ArrayList<FilterCategoryDetails> categoryList;
    private ArrayList<String> selectedBrandList = new ArrayList<>();
    private ArrayList<Integer> selectedPriceList = new ArrayList<>();
    private ArrayList<FilterCategoryDetails> discountList;
    boolean sideTabBrandClick = true;
    private boolean sideTabDiscountClick = true;
    private CommonClassForAPI commonClassForAPI;
    private LinearLayoutManager layoutManagerBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        dbHelper = MySingltonApplication.getInstance().dbHelper;

        if (getIntent().getExtras() != null) {
            categoryId = getIntent().getIntExtra("categoryId", 0);
            discount = getIntent().getStringExtra("discount");
            selectedBrandList = getIntent().getStringArrayListExtra("brandList");
            selectedPriceList = getIntent().getIntegerArrayListExtra("priceList");
        }

        initView();
        setString();
    }

    private void setString() {
        mBinding.tvFilter.setText(dbHelper.getString(R.string.filter));
        mBinding.tvClearAll.setText(dbHelper.getString(R.string.clear_all));
        mBinding.tvFilterType.setText(dbHelper.getString(R.string.select_price_range));
        mBinding.tvCancel.setText(dbHelper.getString(R.string.cancel));
        mBinding.tvApply.setText(dbHelper.getString(R.string.apply));
        mBinding.tvNoData.setText(dbHelper.getString(R.string.no_data_found));
    }

    private void initView() {
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mBinding.rvFilterType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.rvFilterType.addItemDecoration(dividerItemDecoration);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.devider));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvFilterCateData.setLayoutManager(layoutManager);
        filterCategoryAdapter = new CategoryFilterAdapter(this, filterCateDataList, this);
        mBinding.rvFilterCateData.setAdapter(filterCategoryAdapter);

        layoutManagerBrand = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvFilterBrandsData.setLayoutManager(layoutManagerBrand);
        filterBrandAdapter = new BrandsFilterAdapter(this, filterCateDataList, this);
        mBinding.rvFilterBrandsData.setAdapter(filterBrandAdapter);

        LinearLayoutManager layoutManagerDis = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvFilterDiscountData.setLayoutManager(layoutManagerDis);
        filterDiscountAdapter = new DiscountFilterAdapter(this, filterCateDataList, this);
        mBinding.rvFilterDiscountData.setAdapter(filterDiscountAdapter);

        mBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.Category, categoryId);
                intent.putIntegerArrayListExtra(Constant.Price, selectedPriceList);
                intent.putStringArrayListExtra(Constant.Brands, selectedBrandList);
                intent.putExtra(Constant.Discount, discount);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        mBinding.tvCancel.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });

        mBinding.tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBrandList.clear();
                selectedPriceList.clear();
                categoryId = 0;
                discount = "null";
                maxprice = 0;
                minprice = 0;
                setFilterTypeData();
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.clear_all_done));
            }
        });

        mBinding.rvFilterBrandsData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false;
                            skipCount = skipCount + 10;
                            callBrandList();
                        }
                    }
                }
            }
        });
        setFilterTypeData();
    }

    private void setFilterTypeData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Categories");
        list.add("Price");
        list.add("Brands");
        list.add("Discount");
        FilterTypeAdapter filterTypeAdapter = new FilterTypeAdapter(this, list, this);
        mBinding.rvFilterType.setAdapter(filterTypeAdapter);

        String savedData = SharePrefs.getInstance(this).getString(SharePrefs.FILTER_CATEGORY_LIST);
        if (!savedData.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(savedData);
                categoryList = new Gson().fromJson(jsonObject.get("ResultItem").toString(),
                        new TypeToken<List<FilterCategoryDetails>>() {
                        }.getType());
                if (categoryList.size() > 0) {
                    viewVisibility(0);
                    filterCategoryAdapter.setData(categoryList);
                    setSelectedCategory();
                    //  filterCateDataList.addAll(detailsList);
                    //filterCategoryAdapter.notifyDataSetChanged();
                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.FILTER_CATEGORY_LIST, jsonObject.toString());
                } else {
                    viewVisibility(4);
                    mBinding.tvNoData.setText(dbHelper.getString(R.string.no_filter_categories));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (Utils.isNetworkAvailable(getApplicationContext())) {
            commonClassForAPI.getFilterCategoryResult(new DisposableObserver<JsonObject>() {
                @Override
                public void onNext(@NotNull JsonObject jsonObject) {
                    if (jsonObject.get("IsSuccess").getAsBoolean()) {
                        categoryList = new Gson().fromJson(jsonObject.get("ResultItem").getAsJsonArray().toString(),
                                new TypeToken<List<FilterCategoryDetails>>() {
                                }.getType());
                        if (categoryList.size() > 0) {
                            SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.FILTER_CATEGORY_LIST,
                                    jsonObject.toString());
                            viewVisibility(0);
                            // filterCateDataList.addAll(detailsList);
                            //  filterCategoryAdapter.notifyDataSetChanged();
                            filterCategoryAdapter.setData(categoryList);
                            setSelectedCategory();
                        } else {
                            viewVisibility(4);
                            mBinding.tvNoData.setText(dbHelper.getString(R.string.no_filter_categories));
                        }
                    } else {
                        viewVisibility(4);
                        mBinding.tvNoData.setText(dbHelper.getString(R.string.no_filter_categories));
                    }
                }

                @Override
                public void onError(@NotNull Throwable e) {
                    e.printStackTrace();
                    viewVisibility(4);
                }

                @Override
                public void onComplete() {
                    Utils.hideProgressDialog();
                }
            });
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_connection));
        }
    }

    private void callBrandList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            PostBrandModel postBrandModel = new PostBrandModel("", skipCount, takeCount, categoryId);
            commonClassForAPI.getFilterBrandsListResult(new DisposableObserver<JsonObject>() {
                @Override
                public void onNext(@NotNull JsonObject jsonObject) {
                    if (jsonObject.get("IsSuccess").getAsBoolean()) {
                        ArrayList<FilterCategoryDetails> detailsList = new Gson().fromJson(jsonObject.get("ResultItem").getAsJsonArray().toString(), new TypeToken<List<FilterCategoryDetails>>() {
                        }.getType());
                        if (detailsList.size() > 0) {
                            viewVisibility(2);
                            filterCateDataList.addAll(detailsList);
                            setSelectedBrands();
                            filterBrandAdapter.setData(filterCateDataList);
                            // filterCategoryAdapter.notifyDataSetChanged();
                            loading = true;
                        } else {
                            loading = false;
                            viewVisibility(4);
                            mBinding.tvNoData.setText(dbHelper.getString(R.string.no_filter_brands));
                        }
                    } else {
                        loading = false;
                        //viewVisibility(2);
                        // mBinding.tvNoData.setText(dbHelper.getString(R.string.no_filter_brands));
                    }
                }

                @Override
                public void onError(@NotNull Throwable e) {
                    e.printStackTrace();
                    viewVisibility(4);
                }

                @Override
                public void onComplete() {
                    Utils.hideProgressDialog();
                }
            }, postBrandModel);
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_connection));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void clickFilterTypeInterface(String name, int position) {
        System.out.println("Position::" + position);
        switch (position) {
            case 0:
                viewVisibility(0);
                break;
            case 1:
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    commonClassForAPI.getFilterPriceRangeResult(new DisposableObserver<MallMainPriceModel>() {
                        @Override
                        public void onNext(@NotNull MallMainPriceModel result) {
                            if (result.isSuccess()) {
                                viewVisibility(1);
                                setRangeSeekbar(result.getResultItem().getMin(), result.getResultItem().getMax());
                            } else {
                                viewVisibility(4);
                                mBinding.tvNoData.setText(dbHelper.getString(R.string.no_filter_price));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            viewVisibility(4);
                        }

                        @Override
                        public void onComplete() {
                            Utils.hideProgressDialog();
                        }
                    }, categoryId);
                } else {
                    Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_connection));
                }
                break;
            case 2:
                if (sideTabBrandClick) {
                    sideTabBrandClick = false;
                    callBrandList();
                } else {
                    viewVisibility(2);
                }
                break;
            case 3:
                if (sideTabDiscountClick) {
                    sideTabDiscountClick = false;
                    viewVisibility(3);
                    discountList = new ArrayList<>();
                    discountList.add(new FilterCategoryDetails(0, "Any"));
                    discountList.add(new FilterCategoryDetails(0, "10% and above"));
                    discountList.add(new FilterCategoryDetails(0, "20% and above"));
                    discountList.add(new FilterCategoryDetails(0, "30% and above"));
                    discountList.add(new FilterCategoryDetails(0, "40% and above"));
                    discountList.add(new FilterCategoryDetails(0, "50% and above"));
                    discountList.add(new FilterCategoryDetails(0, "60% and above"));
                    discountList.add(new FilterCategoryDetails(0, "70% and above"));
                    filterDiscountAdapter.setData(discountList);
                    setSelectedDiscount();
                } else {
                    viewVisibility(3);

                }
                break;
            default:
                break;
        }
    }

    private void viewVisibility(int type) {
        if (type == 0) {
            mBinding.rvFilterCateData.setVisibility(View.VISIBLE);
            mBinding.rvFilterBrandsData.setVisibility(View.GONE);
            mBinding.rvFilterDiscountData.setVisibility(View.GONE);
            mBinding.priceFilter.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.GONE);
        } else if (type == 1) {
            mBinding.rvFilterCateData.setVisibility(View.GONE);
            mBinding.rvFilterBrandsData.setVisibility(View.GONE);
            mBinding.rvFilterDiscountData.setVisibility(View.GONE);
            mBinding.priceFilter.setVisibility(View.VISIBLE);
            mBinding.tvNoData.setVisibility(View.GONE);
        } else if (type == 2) {
            mBinding.rvFilterCateData.setVisibility(View.GONE);
            mBinding.rvFilterBrandsData.setVisibility(View.VISIBLE);
            mBinding.rvFilterDiscountData.setVisibility(View.GONE);
            mBinding.priceFilter.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.GONE);
        } else if (type == 3) {
            mBinding.rvFilterCateData.setVisibility(View.GONE);
            mBinding.rvFilterBrandsData.setVisibility(View.GONE);
            mBinding.rvFilterDiscountData.setVisibility(View.VISIBLE);
            mBinding.priceFilter.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.GONE);
        } else {
            mBinding.rvFilterCateData.setVisibility(View.GONE);
            mBinding.rvFilterBrandsData.setVisibility(View.GONE);
            mBinding.rvFilterDiscountData.setVisibility(View.GONE);
            mBinding.priceFilter.setVisibility(View.GONE);
            mBinding.tvNoData.setVisibility(View.VISIBLE);
        }
    }

    private void setRangeSeekbar(int min, int max) {
        if (selectedPriceList != null && selectedPriceList.size() > 0) {
             mBinding.rangeSeekbar.setMinThumbValue(15);
            mBinding.rangeSeekbar.setMaxThumbValue(1000);
            mBinding.tvMinMaxRange.setText("₹" + selectedPriceList.get(0) + "- ₹" + selectedPriceList.get(1));
        }else
        {
            mBinding.tvMinMaxRange.setText("₹" + min + "- ₹" + max);
        }
        mBinding.rangeSeekbar.setMinRange(min);
        mBinding.rangeSeekbar.setMax(max);
        minprice = min;
        maxprice = max;
        mBinding.rangeSeekbar.setSeekBarChangeListener(new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {
            }

            @Override
            public void onStoppedSeeking() {
                selectedPriceList.clear();
                if(maxprice!=0){
                    selectedPriceList.add(minprice);
                    selectedPriceList.add(maxprice);
                }
            }

            @Override
            public void onValueChanged(int minValue, int maxValue) {
                int min = mBinding.rangeSeekbar.getMinRange() + minValue;
                mBinding.tvMinMaxRange.setText("₹" + min + "- ₹" + maxValue);
                maxprice = maxValue;
                minprice = mBinding.rangeSeekbar.getMinRange() + minValue;
            }
        });
    }

    private void setSelectedCategory() {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getValue() == categoryId) {
                categoryId = categoryList.get(i).getValue();
                filterCategoryAdapter.lastSelectedPosition = i;
                break;
            }
        }
    }

    private void setSelectedBrands() {
        for (String brand : selectedBrandList) {
            for (int i = 0; i < filterCateDataList.size(); i++) {
                if (brand.equals(filterCateDataList.get(i).getLabel())) {
                    filterCateDataList.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    private void setSelectedDiscount() {
        for (int i = 0; i < discountList.size(); i++) {
            if (discountList.get(i).getLabel().equals(discount)) {
                discount = discountList.get(i).getLabel();
                filterDiscountAdapter.lastSelectedPosition = i;
                break;
            }
        }
    }

    @Override
    public void clickFilterCategoryInterface(int value, String label) {
        if (categoryId != value) {
            categoryId = value;
            sideTabBrandClick = true;
            sideTabDiscountClick = true;
            skipCount = 0;
            filterCateDataList.clear();
            selectedPriceList.clear();
            selectedBrandList.clear();
        }
    }

    @Override
    public void clickFilterBrandyInterface(int cateId, String label, boolean remove) {
        // brand = label;
        if (remove) {
            selectedBrandList.remove(label);
        } else {
            if(label!=null)
            {
                selectedBrandList.add(label);
            }
        }
    }

    @Override
    public void clickFilterDiscountInterface(int cateId, String label) {
        discount = label;
    }
}