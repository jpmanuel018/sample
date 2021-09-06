package ph.sdsolutions.studentsapp.activity.student_list.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph.sdsolutions.studentsapp.R;
import ph.sdsolutions.studentsapp.activity.add_student.AddStudent;
import ph.sdsolutions.studentsapp.activity.student_list.model.GetStudents;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>{

    Context context;
    List<GetStudents> studentsList;

    public StudentListAdapter(Context context, List<GetStudents> studentsList) {
        this.context = context;
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public StudentListAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_list_layout, null);
        return new StudentListAdapter.StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.StudentViewHolder holder, int position) {
        GetStudents students = studentsList.get(position);
        holder.tvStudentName.setText(students.Firstname + " " + students.Lastname);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddStudent.class);
                intent.putExtra("id",students.Id);
                intent.putExtra("first_name",students.Firstname);
                intent.putExtra("last_name",students.Lastname);
                intent.putExtra("middle_name",students.Middlename);
                intent.putExtra("gender",students.Gender);
                intent.putExtra("age",students.Age);
                intent.putExtra("address",students.Address);
                intent.putExtra("course",students.Course);
                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{

        private TextView tvStudentName;
        private ImageView imgEdit, imgDelete;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
