package com.jay.sortandgrouplist.ui
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.jay.sortandgrouplist.IndexBar.widget.IndexBar
import com.jay.sortandgrouplist.R
import com.jay.sortandgrouplist.adapter.CityAdapter
import com.jay.sortandgrouplist.decoration.DividerItemDecoration
import com.jay.sortandgrouplist.model.CityBean
import com.jay.sortandgrouplist.suspension.SuspensionDecoration
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var mRv: RecyclerView? = null

    private var mAdapter: CityAdapter? = null

    private var mManager: LinearLayoutManager? = null

    private var mDatas: MutableList<CityBean>? = null

    private var mDecoration: SuspensionDecoration? = null

    /**
     * 右侧边栏导航区域
     */
    private var mIndexBar: IndexBar? = null

    /**
     * 显示指示器DialogText
     */
    private var mTvSideBarHint: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRv = findViewById(R.id.rv) as RecyclerView
        mManager = LinearLayoutManager(this)
        mRv!!.setLayoutManager(mManager)

        mAdapter = CityAdapter(this, mDatas)
        mRv!!.adapter = mAdapter
        mDecoration = SuspensionDecoration(this, mDatas)
        mRv!!.addItemDecoration(mDecoration)

        //如果add两个，那么按照先后顺序，依次渲染。
        mRv!!.addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL_LIST))

        //使用indexBar
        mTvSideBarHint = findViewById(R.id.tvSideBarHint) as TextView//HintTextView
        mIndexBar = findViewById(R.id.indexBar) as IndexBar//IndexBar

        mIndexBar!!.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager

        initDatas(resources.getStringArray(R.array.provinces))
    }

    /**
     * 组织数据源
     */
    private fun initDatas(data: Array<String>) {
        //延迟200ms 模拟加载数据中....
        window.decorView.postDelayed({
            mDatas = ArrayList<CityBean>()
            for (i in data.indices) {
                val cityBean = CityBean()
                cityBean.setCity(data[i])//设置城市名称
                mDatas!!.add(cityBean)
            }

            mIndexBar!!.setmSourceDatas(mDatas)//设置数据
                    .invalidate()

            mAdapter!!.setDatas(mDatas)
            mAdapter!!.notifyDataSetChanged()
            mDecoration!!.setmDatas(mDatas)
        }, 200)

    }
}
