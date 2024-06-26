package com.example.test;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExamRecyclerViewAdapter extends RecyclerView.Adapter<ExamRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Event> dataArrayList;
    private final RecyclerViewInterface recyclerViewInterface;

    public ExamRecyclerViewAdapter(Context context, ArrayList<Event> dataSet, RecyclerViewInterface r) {
        this.context = context;
        this.dataArrayList = SeeExamsActivity.getMasterList();
        this.recyclerViewInterface = r;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseName;
        private TextView date;
        private TextView location;
        private TextView title;
        private TextView time;

        public ViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            courseName = view.findViewById(R.id.rv_courseNameExam);
            date = view.findViewById(R.id.rv_examDate);
            location = view.findViewById(R.id.rv_examLocation);
            title = view.findViewById(R.id.rv_examTitle);
            time = view.findViewById(R.id.rv_examTime);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.whenClicked(pos);
                        }
                    }
                }
            });
            view.findViewById(R.id.editItemButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddExamActivity.class);

                    intent.putExtra("pos", getAdapterPosition());
                    startActivity(v.getContext(), intent, null);
                }
            });

            view.findViewById(R.id.deleteItemButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeeExamsActivity.getMasterList().remove(getAdapterPosition());
                    SeeExamsActivity.itemRemovedExam(getAdapterPosition());
                }
            });
        }

        public TextView getCourseName() {
            return courseName;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getLocation() {
            return location;
        }

        public TextView getProfessorOrTitle() {
            return title;
        }

        public TextView getTime() {
            return time;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_row_layout_exams, viewGroup, false);

        return new ViewHolder(view, recyclerViewInterface);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Event event = dataArrayList.get(position);
        viewHolder.getCourseName().setText(event.getCourseName());
        viewHolder.getDate().setText((String) ("" + event.getMonth() + "/" + event.getDay() + "/" + event.getYear()));
        viewHolder.getLocation().setText(((Exam) (event)).getBuilding_AND_room());
        viewHolder.getProfessorOrTitle().setText(((Exam) (event)).getTestTitle());
        viewHolder.getTime().setText("" + ((Exam) (event)).getStartHour() + ":" + String.format("%02d", ((Exam) (event)).getStartMinute()));

        Log.d("location test", "" + viewHolder.getLocation().getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

}
