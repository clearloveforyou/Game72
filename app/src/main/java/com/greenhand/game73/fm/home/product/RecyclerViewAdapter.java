package com.greenhand.game73.fm.home.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.greenhand.game73.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * project: Game73
 * package: com.greenhand.game73.fm.home.product
 * author: HouShengLi
 * time: 2017/4/6 15:15
 * e-mail:13967189624@163.com
 * description:
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;

    private View headerView;//头部尾部view
    private List<ItemBean> lists;
    private HeadBean headBean;
    private LayoutInflater inflater;
    private Context mContext;

    public RecyclerViewAdapter(List<ItemBean> lists, HeadBean headBean, Context context) {
        this.lists = lists;
        mContext = context;
        this.headBean = headBean;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEAD) {
            View view = inflater.inflate(R.layout.item_show_product_head, parent, false);
            return new Header(view);
        } else {
            View view = inflater.inflate(R.layout.item_show_product, parent, false);

            return new VH(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_HEAD) {

            Header header = (Header) holder;
            header.txtTitle.setText(headBean.getRemark());
            Glide.with(mContext)
                    .load(headBean.getImgurl())
                    .placeholder(R.mipmap.placeholder_200)
                    .error(R.mipmap.placeholder_200)
                    .into(header.imgBg);
        }
        if (getItemViewType(position) == TYPE_ITEM){
            int p = position-1;
            VH vh = (VH) holder;
            ItemBean itemBean = lists.get(p);
            //
            Glide.with(mContext)
                    .load(itemBean.getHeadImg())
                    .placeholder(R.mipmap.placeholder_200)
                    .error(R.mipmap.placeholder_200)
                    .into(vh.cimgHead);
            vh.txtUsername.setText(itemBean.getNickName());

            //
            ProductBean productBean = itemBean.getProductBeanList().get(0);
            Glide.with(mContext)
                    .load(productBean.getProductImg())
                    .placeholder(R.mipmap.placeholder_200)
                    .error(R.mipmap.placeholder_200)
                    .into(vh.imgContent);
            //
            vh.txtDesc.setText(itemBean.getContent());
            //
            vh.txtTime.setText(itemBean.getCreateDate());
            //
            vh.btnZan.setText(itemBean.getCommentCount());
            //
//            CommentBean commentBean = itemBean.getCommentBeanList().get(0);
//            vh.txtCommentName.setText(commentBean.getNickName() + ":");
//            vh.txtCommentContent.setText(commentBean.getContent());

        }

        //自己现实item监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(holder.itemView, position);
            }
        });

    }


    @Override
    public int getItemCount() {

        return lists.size() + 1;
    }

    /**
     * item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //表示第一个item，并且headerView不为空
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }

    }


    class VH extends RecyclerView.ViewHolder {

        CircleImageView cimgHead;
        TextView txtUsername;
        ImageView imgContent;
        TextView txtDesc;
        TextView txtTime;
        Button btnZan;
        TextView txtCommentName;
        TextView txtCommentContent;

        public VH(View itemView) {
            super(itemView);

            cimgHead = (CircleImageView) itemView.findViewById(R.id.img_show_product_userhead);
            txtUsername = (TextView) itemView.findViewById(R.id.txt_show_product_username);
            imgContent = (ImageView) itemView.findViewById(R.id.img_show_product_content);
            txtDesc = (TextView) itemView.findViewById(R.id.txt_show_product_desc);
            txtTime = (TextView) itemView.findViewById(R.id.txt_show_product_time);
            btnZan = (Button) itemView.findViewById(R.id.btn_show_product_zan);
            txtCommentName = (TextView) itemView.findViewById(R.id.txt_show_product_comment_name);
            txtCommentContent = (TextView) itemView.findViewById(R.id.txt_show_product_comment_content);
        }
    }

    class Header extends RecyclerView.ViewHolder {

        ImageView imgBg;
        TextView txtTitle;

        public Header(View itemView) {
            super(itemView);
            imgBg = (ImageView) itemView.findViewById(R.id.img_show_product_head);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_show_product_title);
        }
    }

    class Footer extends RecyclerView.ViewHolder {

        public Footer(View itemView) {
            super(itemView);
        }
    }

    /**
     * 实现监听
     */
    public interface OnItemClickListener {

        public abstract void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
