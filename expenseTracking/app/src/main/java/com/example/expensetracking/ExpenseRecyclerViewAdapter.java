package com.example.expensetracking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ExpenseViewHolder> {
    ArrayList<Expense> expenses;
    IExpenseRecycler listener;

    public ExpenseRecyclerViewAdapter(ArrayList<Expense> data, IExpenseRecycler listener){
        this.listener = listener;
        this.expenses = data;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);

        ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(view, listener);

        return expenseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.name.setText(expense.getName());
        holder.amount.setText(String.valueOf(expense.getAmount()));
        holder.date.setText(String.format("%d/%d/%d", expense.getDate().get(Calendar.MONTH) + 1, expense.getDate().get(Calendar.DAY_OF_MONTH), expense.getDate().get(Calendar.YEAR)));
        holder.category.setText(expense.getCategory());
        holder.expense = expense;
    }

    @Override
    public int getItemCount() {
        return this.expenses.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView amount;
        TextView date;
        TextView category;
        ImageButton button;
        Expense expense;
        IExpenseRecycler listener;

        public ExpenseViewHolder(@NonNull View itemView, IExpenseRecycler listener){
            super(itemView);
            this.listener = listener;

            name = itemView.findViewById(R.id.textViewExpName);
            amount = itemView.findViewById(R.id.textViewExpAmount);
            date = itemView.findViewById(R.id.textViewExpDate);
            category = itemView.findViewById(R.id.textViewExpCat);
            button = itemView.findViewById(R.id.imageButtonTrash);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.delete(expense);
                }
            });
        }
    }

    interface IExpenseRecycler{
        void delete(Expense exp);
    }
}
