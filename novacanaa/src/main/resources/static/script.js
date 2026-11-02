document.addEventListener('DOMContentLoaded', () => {
    loadTransactions();
    loadFixedTransactions();
    setupForms();
});

// --- GESTÃO DE ABAS ---
function switchTab(tabId) {
    // Esconde todo conteúdo
    document.querySelectorAll('.tab-content').forEach(content => content.classList.add('hidden'));
    // Mostra a aba selecionada
    document.getElementById(`content-${tabId}`).classList.remove('hidden');

    // Atualiza estilo do menu lateral
    document.querySelectorAll('.sidebar-link').forEach(link => {
        link.classList.remove('bg-blue-600/10', 'text-blue-400', 'border', 'border-blue-600/20');
        link.classList.add('text-slate-400');
    });
    const activeLink = document.getElementById(`tab-${tabId}`);
    activeLink.classList.add('bg-blue-600/10', 'text-blue-400', 'border', 'border-blue-600/20');
    activeLink.classList.remove('text-slate-400');
}

// --- GESTÃO DE MODAIS ---
function openModal(type) {
    if (type === 'transacao') {
        document.getElementById('modal').classList.remove('hidden');
        document.getElementById('modal').classList.add('flex');
    } else if (type === 'fixo') {
        document.getElementById('modalFixo').classList.remove('hidden');
        document.getElementById('modalFixo').classList.add('flex');
    }
    document.body.style.overflow = 'hidden';
}

function closeModal() {
    document.getElementById('modal').classList.add('hidden');
    document.getElementById('modal').classList.remove('flex');
    document.getElementById('modalFixo').classList.add('hidden');
    document.getElementById('modalFixo').classList.remove('flex');
    document.body.style.overflow = 'auto';
}

// --- GESTÃO DE DADOS (BACKEND REAL) ---

async function loadTransactions() {
    try {
        const response = await fetch('/movimentacoes');
        const data = await response.json();
        renderTable(data);
        updateSummary(data);
    } catch (error) {
        console.error("Erro ao carregar transações:", error);
    }
}

async function loadFixedTransactions() {
    try {
        const response = await fetch('/movimentacoes-fixas');
        const data = await response.json();
        renderFixedTable(data);
    } catch (error) {
        console.error("Erro ao carregar gastos fixos:", error);
    }
}

function renderTable(transactions) {
    const body = document.getElementById('transactionBody');
    body.innerHTML = '';
    transactions.forEach(t => {
        const isEntrada = t.tipo === 'ENTRADA';
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="px-6 py-4 text-slate-400 font-medium">${formatDate(t.data)}</td>
            <td class="px-6 py-4 text-white font-semibold">${t.descricao}</td>
            <td class="px-6 py-4"><span class="px-2 py-1 rounded-md bg-slate-800 text-slate-400 text-[10px] font-bold uppercase tracking-wider border border-slate-700">${t.categoria || 'Geral'}</span></td>
            <td class="px-6 py-4"><span class="flex items-center gap-1 ${isEntrada ? 'text-green-400' : 'text-red-400'} font-bold"><i class="fas ${isEntrada ? 'fa-arrow-up' : 'fa-arrow-down'} text-[10px]"></i>${t.tipo}</span></td>
            <td class="px-6 py-4 text-right font-bold ${isEntrada ? 'text-green-400' : 'text-red-400'}">${isEntrada ? '+' : '-'} ${formatCurrency(t.valor)}</td>
            <td class="px-6 py-4 text-center">
                <div class="flex justify-center gap-2">
                    <button onclick="deleteTransaction(${t.id})" class="p-2 text-slate-500 hover:text-red-400 transition-colors"><i class="fas fa-trash"></i></button>
                </div>
            </td>`;
        body.appendChild(row);
    });
}

function renderFixedTable(fixas) {
    const body = document.getElementById('fixoBody');
    body.innerHTML = '';
    fixas.forEach(f => {
        const isEntrada = f.tipo === 'ENTRADA';
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="px-6 py-4 text-white font-semibold">${f.descricao}</td>
            <td class="px-6 py-4 text-slate-400 font-medium">Dia ${f.diaDoMes}</td>
            <td class="px-6 py-4"><span class="${isEntrada ? 'text-green-400' : 'text-red-400'} font-bold">${f.tipo}</span></td>
            <td class="px-6 py-4 text-right font-bold text-white">${formatCurrency(f.valor)}</td>
            <td class="px-6 py-4 text-center">
                <span class="px-2 py-1 rounded-full text-[10px] font-bold ${f.ativo ? 'bg-green-500/20 text-green-400' : 'bg-red-500/20 text-red-400'}">
                    ${f.ativo ? 'ATIVO' : 'INATIVO'}
                </span>
            </td>
            <td class="px-6 py-4 text-center">
                <button onclick="deleteFixed(${f.id})" class="p-2 text-slate-500 hover:text-red-400 transition-colors"><i class="fas fa-trash"></i></button>
            </td>`;
        body.appendChild(row);
    });
}

// --- FORMULÁRIOS ---

function setupForms() {
    // Form Transação Normal
    document.getElementById('financeForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = {
            descricao: document.getElementById('descricao').value,
            valor: parseFloat(document.getElementById('valor').value),
            data: document.getElementById('data').value,
            tipo: document.getElementById('tipo').value,
            categoria: document.getElementById('categoria').value
        };
        await saveTransaction(formData);
    });

    // Form Gasto Fixo
    document.getElementById('fixoForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = {
            descricao: document.getElementById('fixoDescricao').value,
            valor: parseFloat(document.getElementById('fixoValor').value),
            diaDoMes: parseInt(document.getElementById('fixoDia').value),
            tipo: document.getElementById('fixoTipo').value,
            ativo: true,
            account: { id: 1 } // Idealmente pegar o ID do usuário logado
        };
        await saveFixedTransaction(formData);
    });
}

async function saveTransaction(data) {
    try {
        const response = await fetch('/movimentacoes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (response.ok) {
            alert('Sucesso!');
            closeModal();
            loadTransactions();
        }
    } catch (error) { alert('Erro ao salvar'); }
}

async function saveFixedTransaction(data) {
    try {
        const response = await fetch('/movimentacoes-fixas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (response.ok) {
            alert('Gasto Fixo configurado!');
            closeModal();
            loadFixedTransactions();
        }
    } catch (error) { alert('Erro ao salvar'); }
}

async function deleteFixed(id) {
    if (confirm('Excluir este gasto fixo?')) {
        await fetch(`/movimentacoes-fixas/${id}`, { method: 'DELETE' });
        loadFixedTransactions();
    }
}

// --- UTILITÁRIOS ---
function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}
function formatDate(dateString) {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString('pt-BR');
}
function updateSummary(transactions) {
    let entradas = 0, saidas = 0;
    transactions.forEach(t => t.tipo === 'ENTRADA' ? entradas += t.valor : saidas += t.valor);
    document.getElementById('totalEntrada').innerText = formatCurrency(entradas);
    document.getElementById('totalSaida').innerText = formatCurrency(saidas);
    document.getElementById('saldo').innerText = formatCurrency(entradas - saidas);
}