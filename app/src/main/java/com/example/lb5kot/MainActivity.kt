package com.example.lb5kot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var yearTxt: TextView;
    var sdf = SimpleDateFormat("dd.MM.yyyy");
    val currentDate = sdf.format(Date());
    lateinit var dateDialog: AlertDialog.Builder;

    lateinit var seekBar: SeekBar;
    lateinit var progressText: EditText;
    lateinit var error: TextView;

    lateinit var toastText: EditText;

    var lostItems: MutableList<String> = ArrayList();
    lateinit var userList: ListView;
    lateinit var editList: EditText;
    lateinit var arrayAdapter: ArrayAdapter<*>;

    var processorClassList: MutableList<String> = ArrayList();
    lateinit var processor: ListView;
    lateinit var arratProcessor: ArrayAdapter<*>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yearTxt = findViewById<TextView>(R.id.daysText);

        dateDialog = AlertDialog.Builder(this);
        dateDialog.setTitle("Дни");
        dateDialog.setMessage("Введите дату");

        var input: EditText = EditText(this);
        dateDialog.setView(input);

        dateDialog.setPositiveButton("Да") { dialog, witch ->
            var date1 = input.getText().split(".").toTypedArray();
            var date2 = currentDate.toString().split(".").toTypedArray();
            var dateArray: Array<Int> = arrayOf(0,0,0);
            dateArray[2] = (date2[2].toInt() - date1[2].toInt()) * 365;
            dateArray[1] = (date2[1].toInt() - date1[1].toInt()) * 30;
            dateArray[0] = date2[0].toInt() - date1[0].toInt();
            var date = dateArray[2] + dateArray[1] + dateArray[0];
            yearTxt.setText(date.toString());
        }

        seekBar = findViewById(R.id.seekBar2);
        progressText = findViewById(R.id.progress);
        error = findViewById(R.id.error);

        toastText = findViewById(R.id.toatsText);

        userList = findViewById(R.id.list_item);
        editList = findViewById(R.id.editList);
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lostItems);
        userList.adapter = arrayAdapter;

        processor = findViewById(R.id.processorsList);
        arratProcessor = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, processorClassList);
        processor.adapter = arratProcessor;
    }

    fun yearBtn(view: View){
        dateDialog.show();
    }

    fun changeProgress(view: View){
        if(!isNum(progressText.text.toString())){
            error.setText("Вы ввели не цифру");
        } else if(progressText.text.toString().toInt() < 0 || progressText.text.toString().toInt() > 100){
            error.setText("Вы ввели число больше 100 \n или меньше 0");
        } else{
            seekBar.setProgress(progressText.text.toString().toInt());
        }
    }

    fun isNum(s: String): Boolean {
        return try{
            s.toInt();
            println("true");
            true;
        } catch (ex: NumberFormatException){
            println("false");
            false;
        }
    }

    fun toatsMessage(view: View){
        Toast.makeText(this, toastText.text.toString(), Toast.LENGTH_SHORT).show();
    }

    fun addItemToList(view: View){
        if (editList.text.toString().equals("")){
            Toast.makeText(this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
        } else{
            lostItems.add(editList.text.toString());
            arrayAdapter.notifyDataSetChanged();
        }
    }

    fun addRandomProcessor(view: View){
        var pr: Processor = Processor(ThreadLocalRandom.current().nextInt(1000, 9999).toString(), ThreadLocalRandom.current().nextInt(1000, 9999));
        processorClassList.add(pr.addToList());
        arratProcessor.notifyDataSetChanged();
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId;
        if(id == R.id.item_done){
            var itemSelected: String = "Выбран: ";
            for (i: Int in 1..processor.count){
                if(processor.isItemChecked(i)){
                    itemSelected += processor.getItemAtPosition(i);
                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    //return super.onCreateOptionsMenu(menu)
    }
}