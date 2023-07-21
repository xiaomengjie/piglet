package com.example.xiao.piglet.ui.genshin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.xiao.piglet.base.BaseFragment
import com.example.xiao.piglet.bean.CharacterCoefficient
import com.example.xiao.piglet.databinding.FragmentGenShinBinding
import com.example.xiao.piglet.tool.toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode

class GenShinFragment : BaseFragment<FragmentGenShinBinding>() {

    private val coefficientMap = mutableMapOf<String, Map<String, Double>>()
    private val characterName = mutableListOf<String>()
    private val entryGradeMap: Map<String, Double> = mapOf(
        "暴击率" to 2.0,
        "暴击伤害" to 1.0,
        "攻击力百分比" to 1.331429,
        "生命值百分比" to 1.331429,
        "防御力百分比" to 1.066362,
        "攻击力" to 0.199146,
        "生命值" to 0.012995,
        "防御力" to 0.162676,
        "元素精通" to 0.332857,
        "元素充能效率" to 1.197943
    )
    private var currentClick = -1;

    override fun initViewBinding(inflater: LayoutInflater): FragmentGenShinBinding {
        return FragmentGenShinBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val popupWindow = GenShinPopupWindow(requireContext())
        popupWindow.setOnItemClickListener{ _, _, position, _ ->
            val entryList = entryGradeMap.keys.toList()
            when(currentClick){
                1 -> {
                    val result = characterName[position]
                    viewBinding.btnSelectCharacter.text = result
                }
                2 -> {
                    val result = entryList[position]
                    viewBinding.btnSelectEntry1.text = result
                }
                3 -> {
                    val result = entryList[position]
                    viewBinding.btnSelectEntry2.text = result
                }
                4 -> {
                    val result = entryList[position]
                    viewBinding.btnSelectEntry3.text = result
                }
                5 -> {
                    val result = entryList[position]
                    viewBinding.btnSelectEntry4.text = result
                }
            }
            popupWindow.dismiss()
        }
        viewBinding.btnSelectCharacter.setOnClickListener {
            currentClick = 1
            showPopup(popupWindow, it as Button, true)
        }
        viewBinding.btnSelectEntry1.setOnClickListener {
            currentClick = 2
            showPopup(popupWindow, it as Button, false)
        }
        viewBinding.btnSelectEntry2.setOnClickListener {
            currentClick = 3
            showPopup(popupWindow, it as Button, false)
        }
        viewBinding.btnSelectEntry3.setOnClickListener {
            currentClick = 4
            showPopup(popupWindow, it as Button, false)
        }
        viewBinding.btnSelectEntry4.setOnClickListener {
            currentClick = 5
            showPopup(popupWindow, it as Button, false)
        }
        viewBinding.btnCalculate.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        // 获取角色名
        val characterName = viewBinding.btnSelectCharacter.text.toString()
        if (characterName == "请点击"){
            "请先选择角色".toast(requireContext())
            return
        }
        // 获取角色对于不同词条的权重
        val coefficient = coefficientMap.getValue(characterName)

        // 获取词条名
        val entry1Name = viewBinding.btnSelectEntry1.text.toString()
        // 获取词条值
        val entry1Value = viewBinding.etEntry1.text.toString().run {
            if (isEmpty()){
                0.0
            }else{
                toDouble()
            }
        }
        // 计算词条得分（词条值 * 词条分 * 角色对于该词条的权重）
        val entry1Grade = if (entry1Name == "请点击"){
            0.0
        }else{
            entry1Value * entryGradeMap.getValue(entry1Name) * coefficient.getValue(entry1Name)
        }

        val entry2Name = viewBinding.btnSelectEntry2.text.toString()
        val entry2Value = viewBinding.etEntry2.text.toString().run {
            if (isEmpty()){
                0.0
            }else{
                toDouble()
            }
        }
        val entry2Grade = if (entry2Name == "请点击"){
            0.0
        }else{
            entry2Value * entryGradeMap.getValue(entry2Name) * coefficient.getValue(entry2Name)
        }

        val entry3Name = viewBinding.btnSelectEntry3.text.toString()
        val entry3Value = viewBinding.etEntry3.text.toString().run {
            if (isEmpty()){
                0.0
            }else{
                toDouble()
            }
        }
        val entry3Grade = if (entry3Name == "请点击"){
            0.0
        }else{
            entry3Value * entryGradeMap.getValue(entry3Name) * coefficient.getValue(entry3Name)
        }

        val entry4Name = viewBinding.btnSelectEntry4.text.toString()
        val entry4Value = viewBinding.etEntry4.text.toString().run {
            if (isEmpty()){
                0.0
            }else{
                toDouble()
            }
        }
        val entry4Grade = if (entry4Name == "请点击"){
            0.0
        }else{
            entry4Value * entryGradeMap.getValue(entry4Name) * coefficient.getValue(entry4Name)
        }

        viewBinding.tvResult.text =
            "${entry1Grade.toBigDecimal().setScale(2, RoundingMode.HALF_UP)} + " +
                    "${entry2Grade.toBigDecimal().setScale(2, RoundingMode.HALF_UP)} + " +
                    "${entry3Grade.toBigDecimal().setScale(2, RoundingMode.HALF_UP)} + " +
                    "${entry4Grade.toBigDecimal().setScale(2, RoundingMode.HALF_UP)} = " +
                    "${(entry1Grade + entry2Grade + entry3Grade + entry4Grade).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}"
    }

    private fun showPopup(popupWindow: GenShinPopupWindow, button: Button, isCharacter: Boolean){
        if (isCharacter){
            lifecycleScope.launch {
                loadCharacterCoefficient()
                popupWindow.addAll(characterName)
                popupWindow.showAsDropDown(button)
            }
        }else{
            popupWindow.addAll(entryGradeMap.keys.toList())
            popupWindow.showAsDropDown(button)
        }
    }

    private suspend fun loadCharacterCoefficient() = withContext(Dispatchers.IO){
        if (coefficientMap.isEmpty() || characterName.isEmpty()){
            val bufferedReader =
                requireContext().assets.open("characters.json").bufferedReader()
            val gson = Gson()
            val result = (gson.fromJson(
                bufferedReader,
                object : TypeToken<Map<String, CharacterCoefficient>>(){}.type) as Map<String, CharacterCoefficient>)
                .filter { it.value.isValid }
            characterName.addAll(result.keys)
            result.entries.associateTo(coefficientMap){
                it.key to mapOf(
                    "暴击率" to it.value.critical_chance,
                    "暴击伤害" to it.value.critical_hit,
                    "攻击力百分比" to it.value.atk,
                    "生命值百分比" to it.value.hp,
                    "防御力百分比" to it.value.defence,
                    "攻击力" to it.value.atk,
                    "生命值" to it.value.hp,
                    "防御力" to it.value.defence,
                    "元素精通" to it.value.element_mastery,
                    "元素充能效率" to it.value.element_charging
                )
            }
        }
    }
}