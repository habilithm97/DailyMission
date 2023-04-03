package com.example.simpletodolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletodolist.databinding.MainItemBinding;

public class MainAdapter extends ListAdapter<MainModel, MainAdapter.ViewHolder> {

    //private List<MainModel> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener; // 전달된 리스너 객체를 저장할 변수

    public MainAdapter() {
        super(DIFF_CALLBACK);
    }

    // 어댑터에서 현재 리스트와 교체될 리스트를 비교하여 변경사항을 알아내는 클래스
    // -> RecyclerView에서 기존 리스트에 변화가 있을 때, 전체를 갈아치우는게 아니라 변경된 데이터만 빠르게 바꿔줌
    // -> 바뀐 부분만 골라서 리스트를 갱신함
    private static final DiffUtil.ItemCallback<MainModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MainModel>() {
        @Override // 두 아이템이 같은 객체인지
        public boolean areItemsTheSame(@NonNull MainModel oldItem, @NonNull MainModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override // 두 아이템이 같은 데이터를 가지고 있는지
        public boolean areContentsTheSame(@NonNull MainModel oldItem, @NonNull MainModel newItem) {
            return oldItem.getContent().equals(newItem.getContent());
        }
    };

    // DataBinding을 위해 TextView와 itemView 대신 MainItemBinding을 사용함
    class ViewHolder extends RecyclerView.ViewHolder {

        private final MainItemBinding itemBinding;

        public ViewHolder(@NonNull MainItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            // 아이템 클릭 이벤트 핸들러 메서드에서 리스너 객체 메서드 호출
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.main_item, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainModel item = getItem(position);
        holder.itemBinding.setItem(item);
    }

    public MainModel getPosition(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener { // 커스텀 리스너 인터페이스 정의
        void onItemClick(MainModel mainModel);
    }

    // 리스너 객체를 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /*
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MainModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }
     */
}
