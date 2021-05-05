package com.db.extrato.scheduler;

import java.io.IOException;
import java.util.logging.Level;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.extrato.enums.VrHorario;
import com.db.extrato.service.DerivativesService;

import lombok.extern.java.Log;
import net.sf.jasperreports.engine.JRException;

@Log
@Component
public class DerivativesQuartzJob implements Job {

	@Autowired
	private DerivativesService derivativesService;
	
	private String periodo;

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			derivativesService.generateScheduledJob(VrHorario.valueOf(periodo));
		} catch (IOException | JRException e) {
			 log.log(Level.SEVERE, "Erro ao gerar relatório agendado", e);
			 log.info("Erro ao gerar relatório agendado");
			 log.info("Erro: " + e.getMessage());
		}
    }

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
}
